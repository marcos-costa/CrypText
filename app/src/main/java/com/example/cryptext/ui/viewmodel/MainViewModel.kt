package com.example.cryptext.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptext.backendIntegration.SocketHandler
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.ServerSharedKey
import com.example.cryptext.data.UserDataRepository
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.User
import com.example.cryptext.encrypt.DiffieHellman
import com.example.cryptext.encrypt.encryptBlowfish
import com.example.cryptext.encrypt.hashSenhaSHA256
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.SecureRandom
import java.time.Instant


class MainViewModel(
    private val database: AppDatabase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var TAG = "CrypText - ViewModel"

    lateinit var privateKey: BigInteger
    var myData: Flow<Map<String, String>>

    init {
        myData = userDataRepository.userData
        viewModelScope.launch {
            privateKey = userDataRepository.privateKey.first().toBigInteger()
            if (privateKey == BigInteger("0")){
                privateKey = BigInteger.probablePrime(16, SecureRandom())
                userDataRepository.savePrivateKey(privateKey)
            }
            SocketHandler.connect("http://3.137.189.73:3000/", privateKey, database )
        }
    }

    val friends: Flow<List<Friend>> = database.friendDao().getAllFriend()
    val friendsRequest: Flow<List<User>> = database.userDao().getAllReceivedSolicitations()

    val usersList: Flow<List<User>> = database.userDao().getAllUsers()

    val unreadMessagesCount: Flow<Int> = database.messageDao().getUnreadMessagesCount()
    val pendingSolicitationsCount: Flow<Int> = database.userDao().getPendingSolicitatinonsCount()

    val lastChat: Flow<List<Message>> = database.messageDao().getLastMessages()

    var isRegistrated = MutableStateFlow(false)
    var isLogged = MutableStateFlow(false)

    lateinit var friend: Flow<Friend>
    lateinit var  friendMessages: Flow<List<Message>>

    fun getFriendMessage(username: String){
        friendMessages = database.messageDao().getFriendMessages(username)
    }

    fun getFriend(username: String){
        friend = database.friendDao().getFriendFlow(username)
    }

    fun readFriendMessages(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var messages = database.messageDao()
                .getFriendMessages(username)
                .map { entities ->
                    entities.map { entity ->
                        entity.copy(read = true)
                    }
                }.collect{ list ->
                    database.messageDao().updateAll(list)
                }
        }
    }

    fun sendMessage(content: String, friend: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Enviando mensagem ao servidor")

            var sender = myData.first()["username"] ?: ""
            var receiver = friend
            val timestamp = Instant.now().toEpochMilli().toString()

            Log.d(TAG, "Dados da requisição -> sender: $sender, receiver: $receiver, timestamp: $timestamp, content: $content")

            sender = encryptBlowfish(ServerSharedKey.value, sender)
            receiver = encryptBlowfish(ServerSharedKey.value, receiver)
            val encryptedTimestamp = encryptBlowfish(ServerSharedKey.value, timestamp)

            val sharedKey = database.friendDao().getFriend(friend).sharedKey
            val encryptedContent = encryptBlowfish(sharedKey, content)

            Log.d(TAG, "Dados criptografados da requisição -> sender: $sender, receiver: $receiver, timestamp: $encryptedTimestamp, content: $encryptedContent")

            SocketHandler.sendMessage(sender, receiver, encryptedTimestamp, encryptedContent) {
                database.messageDao().insert(
                    Message(
                        friend = friend,
                        timestamp = Instant.now().toEpochMilli(),
                        received = false,
                        read = true,
                        content = content
                    )
                )
            }
        }
    }

    fun singUp(name: String, username: String, email: String, password: String){
        val hashPassword = hashSenhaSHA256(password)

        Log.d(TAG, "Sending Register user request")
        Log.d(TAG, "User Data -> name: $name, username: $username, email: $email, password: $hashPassword")

        val encryptedName = encryptBlowfish(ServerSharedKey.value, name)
        val encryptedUsername = encryptBlowfish(ServerSharedKey.value, username)
        val encryptedEmail = encryptBlowfish(ServerSharedKey.value, email)
        val encryptedPassword = encryptBlowfish(ServerSharedKey.value, hashPassword)

        Log.d(TAG, "User Data Encrypted -> name: $encryptedName, username: $encryptedUsername, email: $encryptedEmail, password: $encryptedPassword")

        SocketHandler.singUp(encryptedName, encryptedEmail, encryptedPassword, encryptedUsername, "") {
            isRegistrated.value = it
        }
    }

    fun login(email: String, password: String){

        val hashPassword = hashSenhaSHA256(password)

        Log.d(TAG, "Sending Login user request")
        Log.d(TAG, "User Data -> email: $email, password: $hashPassword")

        val encryptedEmail = encryptBlowfish(ServerSharedKey.value, email)
        val encryptedPassword = encryptBlowfish(ServerSharedKey.value, hashPassword)

        Log.d(TAG, "User Data Encrypted -> email: $encryptedEmail, password: $encryptedPassword")

        SocketHandler.login(encryptedEmail, encryptedPassword) { logged, name, username ->
            isLogged.value = logged
            if (logged) {
                userDataRepository.saveUserData(
                    name = name,
                    username = username,
                    email = email,
                    password = password
                )

                Log.d(TAG, "Obtendo os dados enviados quando o usuário estava offline")

                Log.d(TAG, "Dados enviados na requisição -> username: $username")

                val encryptedUsername = encryptBlowfish(ServerSharedKey.value, username)

                Log.d(TAG, "Dados criptografados enviados na requisição -> username: $encryptedUsername")

                SocketHandler.getOfflineData(encryptedUsername)
            }
        }
    }

    fun listUsers() {
        viewModelScope.launch {
            Log.d(TAG, "Requisitando lista de usuários do Servidor")
            SocketHandler.listUsers(
                encryptBlowfish(ServerSharedKey.value, myData.first()["username"] ?: "")
            )
        }
    }

    fun requestFriend(user: User){
        viewModelScope.launch {
            Log.d(TAG, "Enviando requisição de amizade ao servidor")

            var friend1 = myData.first()["username"] ?: ""
            var friend2 = user.username
            val (p, g) = DiffieHellman().generatePG()
            val pString = p.toString()
            val gString = g.toString()
            var publicKey = DiffieHellman().calculatePublicKey(p, g, privateKey).toString()

            Log.d(TAG, "Dados da requisição de amizade -> friend1: $friend1, friend2: $friend2, p: $pString, g: $gString, publicKey: $publicKey")

            friend1 = encryptBlowfish(ServerSharedKey.value, friend1)
            friend2 = encryptBlowfish(ServerSharedKey.value, friend2)
            val pStringEncrypt = encryptBlowfish(ServerSharedKey.value, pString)
            val gStringEncrypt = encryptBlowfish(ServerSharedKey.value, gString)
            publicKey = encryptBlowfish(ServerSharedKey.value, publicKey)

            Log.d(TAG, "Dados da requisição de amaizade -> friend1: $friend1, friend2: $friend2, p: $pString, g: $gString, publicKey: $publicKey")

            SocketHandler.requestFriend(friend1, friend2, pStringEncrypt, gStringEncrypt, publicKey) {
                database.userDao().delete(user)
                database.userDao().insert(
                    User(
                        name = user.name,
                        username = user.username,
                        email = user.email,
                        p = pString,
                        g = gString
                    )
                )
            }
        }
    }

    fun acceptFriend(user: User) {
        Log.d(TAG, "Enviando requisição de aceitar amizade ao servidor")

        viewModelScope.launch {
            var friend1 = user.username
            var friend2 = myData.first()["username"] ?: ""
            var publicKey = DiffieHellman().calculatePublicKey(user.p!!.toBigInteger(), user.g!!.toBigInteger(), privateKey).toString()

            Log.d(TAG, "Dados da requisição de aceitar amizade -> friend1: $friend1, friend2: $friend2, publicKey: $publicKey")

            friend1 = encryptBlowfish(ServerSharedKey.value, friend1)
            friend2 = encryptBlowfish(ServerSharedKey.value, friend2)
            publicKey = encryptBlowfish(ServerSharedKey.value, publicKey)

            Log.d(TAG, "Dados da requisição de aceitar amizade -> friend1: $friend1, friend2: $friend2, publicKey: $publicKey")

            SocketHandler.acceptFriendRequest(friend1, friend2, publicKey) {
                val sharedKey = DiffieHellman().calculateSharedSecret(user.p.toBigInteger(), privateKey, user.publicKey!!.toBigInteger())
                val friend = Friend(
                    name = user.name,
                    email = user.email,
                    username = user.username,
                    sharedKey = sharedKey.toString()
                )
                database.friendDao().insert(friend)
                database.userDao().delete(user)
            }
        }
    }
    fun declineFriend(user: User) {
        Log.d(TAG, "Enviando requisição de rejeitar amizade ao servidor")

        viewModelScope.launch {
            var friend1 = user.username
            var friend2 = myData.first()["username"] ?: ""

            Log.d(TAG, "Dados da requisição de rejeitar amizade -> friend1: $friend1, friend2: $friend2")

            friend1 = encryptBlowfish(ServerSharedKey.value, friend1)
            friend2 = encryptBlowfish(ServerSharedKey.value, friend2)

            Log.d(TAG, "Dados da requisição de aceitar amizade -> friend1: $friend1, friend2: $friend2")

            SocketHandler.declineFriendRequest(friend1, friend2) {
                database.userDao().insert(
                    User(
                        name = user.name,
                        username = user.username,
                        email = user.email
                    )
                )
            }
        }
    }
}
