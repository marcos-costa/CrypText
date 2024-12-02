package com.example.cryptext.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.UserDataRepository
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import com.example.cryptext.backendIntegration.SocketHandler
import com.example.cryptext.data.ServerSharedKey
import com.example.cryptext.encrypt.encryptBlowfish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import java.math.BigInteger
import java.security.SecureRandom


class MainViewModel(
    private val database: AppDatabase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var TAG = "CrypText - ViewModel"

    lateinit var privateKey: BigInteger

    init {
        viewModelScope.launch {
            privateKey = userDataRepository.privateKey.first().toBigInteger()
            if (privateKey == BigInteger("0")){
                privateKey = BigInteger.probablePrime(16, SecureRandom())
                userDataRepository.savePrivateKey(privateKey)
            }

            SocketHandler.connect("http://192.168.1.26:3000")
            SocketHandler.diffieHellmanKeyExchange(privateKey)
        }
    }

    val friends: Flow<List<Friend>> = database.friendDao().getAllFriend()
    val friendsRequest: Flow<List<User>> = database.userDao().getAllReceivedSolicitations()

    val friendSolicitations: Flow<List<User>> = database.userDao().getAllPendingSolicitations()

    val unreadMessagesCount: Flow<Int> = database.messageDao().getUnreadMessagesCount()
    val pendingSolicitationsCount: Flow<Int> = database.userDao().getPendingSolicitatinonsCount()

    val lastChat: Flow<List<Message>> = database.messageDao().getLastMessages()

    var myData = userDataRepository.userData

    var isRegistrated = MutableStateFlow(false)
    var isLogged = MutableStateFlow(false)


    lateinit var friend: Flow<Friend>
    lateinit var  friendMessages: Flow<List<Message>>

    fun sendMessage(content: String, friend: String) {
        val message = Message(
            friend = friend,
            timestamp = Instant.now().toEpochMilli(),
            received = false,
            read = true,
            content = content
        )
        viewModelScope.launch(Dispatchers.IO) {
            database.messageDao().insert(message)
        }
    }

    fun singUp(name: String, username: String, email: String, password: String){
        Log.d(TAG, "Sending Register user request")
        Log.d(TAG, "User Data -> name: $name, username: $username, email: $email, password: $password")

        val encryptedName = encryptBlowfish(ServerSharedKey.value.toString(), name)
        val encryptedUsername = encryptBlowfish(ServerSharedKey.value.toString(), username)
        val encryptedEmail = encryptBlowfish(ServerSharedKey.value.toString(), email)
        val encryptedPassword = encryptBlowfish(ServerSharedKey.value.toString(), password)

        Log.d(TAG, "User Data Encrypted -> name: $encryptedName, username: $encryptedUsername, email: $encryptedEmail, password: $encryptedPassword")

        SocketHandler.singUp(encryptedName, encryptedEmail, encryptedPassword, encryptedUsername, "") {
            isRegistrated.value = it
        }
    }

    fun login(email: String, password: String){
        Log.d(TAG, "Sending Login user request")
        Log.d(TAG, "User Data -> email: $email, password: $password")

        val encryptedEmail = encryptBlowfish(ServerSharedKey.value.toString(), email)
        val encryptedPassword = encryptBlowfish(ServerSharedKey.value.toString(), password)

        Log.d(TAG, "User Data Encrypted -> email: $encryptedEmail, password: $encryptedPassword")

        SocketHandler.login(encryptedEmail, encryptedPassword) {
            isLogged.value = it
        }
    }


    fun getFriend(username: String){
        friend = database.friendDao().getFriend(username)
    }

    fun getFriendMessage(username: String){
        friendMessages = database.messageDao().getFriendMessages(username)
    }

    fun acceptFriend(user: User) {
        val friend = Friend(
            id = user.id,
            name = user.name,
            email = user.email,
            username = user.username,
            sharedKey = "2"
        )
        viewModelScope.launch(Dispatchers.IO) {
            database.friendDao().insert(friend)
            database.userDao().delete(user)
        }
    }
    fun declineFriend(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().delete(user)
        }
    }
}
