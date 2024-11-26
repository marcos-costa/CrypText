package com.example.cryptext

import android.util.Log
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.math.BigInteger
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    init{
        this.setSocket()
    }

    @Synchronized
    fun setSocket() {

        val options = IO.Options()
        options.transports = arrayOf("websocket")
        try {
            mSocket = IO.socket("http://192.168.1.62:3000", options) // trocar IP
        } catch (e: URISyntaxException) {
            Log.d("error", "URISyntaxException: ${e.message}")
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection(privateKey: BigInteger) {
        mSocket.on(Socket.EVENT_CONNECT) {
            Log.d("connect", "Connection successful")
            val dhClient = DiffieHellmanClient(socket = mSocket)
            dhClient.startDiffieHellman(privateKey)
        }

        mSocket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.d("connect", "Connection failed: ${args[0]}")
        }

        mSocket.on(Socket.EVENT_DISCONNECT) {
            Log.d("connect", "Disconnected from server")
        }

        // Listener para erros de WebSocket específicos
        mSocket.on("error") { args ->
            Log.d("websocket", "WebSocket error: ${args[0]}")
        }

        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }

    //como retornar json contendo amigos online?
    @Synchronized
    fun listOnlineFriends(){
        mSocket.emit("online-users", object : Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    Log.d("json of online users",""+response)
                }
            }
        })
    }

    @Synchronized
    fun login(email:String, password:String):Pair<String,String>{
        var user_name:String = ""
        var name:String = ""
        mSocket.emit("login", arrayOf(email,password), object : Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
                        user_name = response.getString("user_name")
                        name = response.getString("name")
                        Log.d("login success", "entered account")
                    }
                    else{
                        Log.d("login error", "unable to enter account")
                    }
                }
            }
        })
        return Pair(user_name, name)
    }

    @Synchronized
    fun registerUser(name:String, email:String, password:String, user_name:String,image:String):Boolean{
        var success:Boolean = true
        mSocket.emit("register", arrayOf(name, email, password, user_name,image), object : Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
                        Log.d("register success", "registered account")
                    }
                    else{
                        Log.d("register error", "unable to register account")
                        success = false
                    }
                }
            }
        })
        return success
    }

    @Synchronized
    fun requestFriend(user_name1:String, user_name2:String, p_value:BigInteger, g_value:BigInteger, publicKey_friend1:BigInteger):Boolean{
        var success:Boolean = true
        mSocket.emit("friend-request", arrayOf(user_name1, user_name2, p_value, g_value, publicKey_friend1), object :
            Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
                        Log.d("request success", "friendship request registered")
                    }
                    else{
                        Log.d("request error", "friendship request error")
                        success = false
                    }
                }
            }
        })
        return success
    }

    @Synchronized
    fun onlineAndLoged(user_name: String):Boolean{
        var success:Boolean = true
        mSocket.emit("online-loged", arrayOf(user_name), object : Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
//                        offlineMessages:  encryptDataBlowfish(await getOfflineMessages(user_name),sharedSecret),
//                        friendRequests: encryptDataBlowfish(await getPendingFriendRequests(user_name),sharedSecret),
//                        acceptedRequests: encryptDataBlowfish(await getAcceptedFriendRequests(user_name), sharedSecret)
                        Log.d("request success", "friendship request registered")
                    }
                    else{
                        Log.d("request error", "friendship request error")
                        success = false
                    }
                }
            }
        })
        return success
    }

    @Synchronized
    fun acceptFriend(user_name1:String, user_name2:String, publicKey_friend2:BigInteger):Boolean{
        var success:Boolean = true
        mSocket.emit("accept-friend", arrayOf(user_name1, user_name2, publicKey_friend2), object :
            Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
                        Log.d("friendship", "friendship accepted")
                    }
                    else{
                        Log.d("friendship error", "friendship acceptance error")
                        success = false
                    }
                }
            }
        })
        return success
    }

    @Synchronized
    fun listFriends(user_name: String):String{
        var lists:String = ""
        mSocket.emit("accept-friend", user_name, object : Ack {
            override fun call(vararg args: Any?) {
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val response = args[0] as JSONObject
                    if (response.getString("success") == "true"){
                        Log.d("friendship", "friendship accepted")
                        lists = response.getString("friends")
                    }
                    else{
                        Log.d("friendship error", "friendship acceptance error")
                    }
                }
            }
        })
        return lists

    }
}