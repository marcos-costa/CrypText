package com.example.cryptext.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptext.SocketHandler
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.MyData
import com.example.cryptext.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

class MainViewModel(
    private val database: AppDatabase,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (database.myDataDao().count() == 0){
                database.myDataDao().insert(MyData(privateKey = Random.nextInt(1000000, 10000000 + 1)))
            }
        }

        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val mSocket = SocketHandler.getSocket()
    }

    val friends: Flow<List<Friend>> = database.friendDao().getAllFriend()
    val friendsRequest: Flow<List<User>> = database.userDao().getAllReceivedSolicitations()

    val friendSolicitations: Flow<List<User>> = database.userDao().getAllPendingSolicitations()

    val unreadMessagesCount: Flow<Int> = database.messageDao().getUnreadMessagesCount()
    val pendingSolicitationsCount: Flow<Int> = database.userDao().getPendingSolicitatinonsCount()

    val lastChat: Flow<List<Message>> = database.messageDao().getLastMessages()

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
        viewModelScope.launch(Dispatchers.IO) {
            val data = database.myDataDao().getMyData()
            val newData = MyData(
                name = name,
                username = username,
                email = email,
                password = password,
                privateKey = data.privateKey
            )
            database.myDataDao().insert(newData)
        }
    }

    fun login(email: String, password: String){

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
