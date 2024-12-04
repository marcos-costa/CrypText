package com.example.cryptext.backendIntegration

import android.util.Log
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.ServerSharedKey
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.User
import com.example.cryptext.encrypt.DiffieHellman
import com.example.cryptext.encrypt.decryptBlowfish
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import java.math.BigInteger

object SocketHandler {

    private var TAG = "Backend Integration"

    lateinit private var socket: Socket
    lateinit var database: AppDatabase

    fun connect(serverUrl: String, privateKey: BigInteger, appDatabase: AppDatabase) {
        database = appDatabase

        socket = IO.socket(serverUrl)
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            Log.d(TAG, "Connected to Server")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d(TAG, "Disconnected to Server")
        }

        socket.on("receive-friend-request") { args ->
            Log.d(TAG, "Solicitação de amizade recebida")
            if (args.isNotEmpty() && args[0] is JSONObject) {
                val data = args[0] as JSONObject

                var name = data.getString("name")
                var username = data.getString("user_name")
                var email = data.getString("email")
                var p = data.getString("p_value")
                var g = data.getString("g_value")
                var publicKey = data.getString("public_key")

                Log.d(TAG, "Dados criptografados recebidos do servidor -> name: $name, username: $username, email: $email, p: $p, g: $g, publicKey: $publicKey")

                name = decryptBlowfish(ServerSharedKey.value, name)
                username = decryptBlowfish(ServerSharedKey.value, username)
                email = decryptBlowfish(ServerSharedKey.value, email)
                p = decryptBlowfish(ServerSharedKey.value, p)
                g = decryptBlowfish(ServerSharedKey.value, g)
                publicKey = decryptBlowfish(ServerSharedKey.value, publicKey)

                Log.d(TAG, "Dados criptografados recebidos do servidor -> name: $name, username: $username, email: $email, p: $p, g: $g, publicKey: $publicKey")

                val user = User(
                    name = name,
                    username = username,
                    email = email,
                    p = p,
                    g = g,
                    publicKey = publicKey
                )

                database.userDao().insert(user)
            } else {
                Log.d(TAG, "Nenhum dado recebido")
            }
        }

        socket.on("accepted-friendship") { args ->
            Log.d(TAG, "Amizade solicidatada foi aceita")
            if (args.isNotEmpty() && args[0] is JSONObject) {
                val data = args[0] as JSONObject

                var friend = data.getString("user_name")
                var publicKey = data.getString("publicKeyFriend")

                Log.d(TAG, "Dados criptografados recebidos do servidor -> friendUsername: $friend, friendPublickey: $publicKey")

                friend = decryptBlowfish(ServerSharedKey.value, friend)
                publicKey = decryptBlowfish(ServerSharedKey.value, publicKey)

                Log.d(TAG, "Dados descriptografados recebidos do servidor -> friendUsername: $friend, friendPublickey: $publicKey")

                val user = database.userDao().getUser(friend)

                val sharedKey = DiffieHellman().calculateSharedSecret(user.p!!.toBigInteger(), privateKey, publicKey.toBigInteger())

                val newFriend = Friend(
                    name = user.name,
                    username = user.username,
                    email = user.email,
                    sharedKey = sharedKey.toString()
                )

                database.friendDao().insert(newFriend)
                database.userDao().delete(user)

                Log.d(TAG, "Usuário adicionado aos amigos com sucesso")
            } else {
                Log.d(TAG, "Nenhum dado recebido")
            }
        }

        socket.on("refused-friendship") { args ->
            Log.d(TAG, "Amizade solicidatada foi recusada")
            if (args.isNotEmpty() && args[0] is JSONObject) {
                val data = args[0] as JSONObject

                var friend = data.getString("user_name")

                Log.d(TAG, "Dados criptografados recebidos do servidor -> friendUsername: $friend")

                friend = decryptBlowfish(ServerSharedKey.value, friend)

                Log.d(TAG, "Dados descriptografados recebidos do servidor -> friendUsername: $friend")

                val user = database.userDao().getUser(friend)

                val newUser = User(
                    name = user.name,
                    username = user.username,
                    email = user.email,
                )

                database.userDao().insert(newUser)

                Log.d(TAG, "Solicitação de amizade removida com sucesso")
            } else {
                Log.d(TAG, "Nenhum dado recebido")
            }
        }

        socket.on("receive-message") { args ->
            Log.d(TAG, "Uma nova mensagem recebida")
            if (args.isNotEmpty() && args[0] is JSONObject) {
                val data = args[0] as JSONObject

                var sender = data.getString("sender")
                var timestamp = data.getString("timestamp")
                var content = data.getString("content")

                Log.d(TAG, "Dados criptografados recebidos do servidor -> sender: $sender, timestamp: $timestamp, content: $content")

                sender = decryptBlowfish(ServerSharedKey.value, sender)
                timestamp = decryptBlowfish(ServerSharedKey.value, timestamp)

                val sharedKey = database.friendDao().getFriend(sender).sharedKey
                content = decryptBlowfish(sharedKey, content)

                Log.d(TAG, "Dados descriptografados recebidos do servidor -> sender: $sender, timestamp: $timestamp, content: $content")

                database.messageDao().insert(
                    Message(
                        friend = sender,
                        timestamp = timestamp.toLong(),
                        received = true,
                        read = false,
                        content = content
                    )
                )

                Log.d(TAG, "Mensagem inserida com sucesso")
            } else {
                Log.d(TAG, "Nenhum dado recebido")
            }
        }

        diffieHellmanKeyExchange(privateKey)
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun diffieHellmanKeyExchange(privateKey: BigInteger) {
        Log.d(TAG, "Starting Diffie-Hellman Key Exchange")

        val (p, g) = DiffieHellman().generatePG()
        val publicKey = DiffieHellman().calculatePublicKey(p, g, privateKey)

        socket.emit("diffie-hellman", arrayOf(p, g, publicKey)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    val serverPublicKey = jsonResponse.getInt("PublicKeyServer").toBigInteger()
                    Log.d(TAG, "Received server public key: $serverPublicKey")

                    val sharedServerKey = DiffieHellman().calculateSharedSecret(p, privateKey, serverPublicKey)
                    Log.d(TAG, "Shared server key: $sharedServerKey")

                    ServerSharedKey.value = sharedServerKey.toString()
                } else {
                    Log.d(TAG, "Fail Diffie-Hellman.")
                }
            } else {
                Log.d(TAG, "No value in server response")
            }
        }
    }

    fun singUp(encryptedName: String, encryptedEmail: String, encryptedPassword: String, encryptedUsername: String, encryptedImage: String, onReponse: (Boolean) -> Unit) {
        socket.emit("register", arrayOf(encryptedName, encryptedEmail, encryptedPassword, encryptedUsername, encryptedImage)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Usuário Cadastrado com Sucesso")
                    onReponse(true)
                } else {
                    Log.d(TAG, "Falha ao Cadastrar usuário")
                    onReponse(false)
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun login(encryptedEmail: String, encryptedPassword: String, onReponse: (Boolean, String, String) -> Unit){
        socket.emit("login", arrayOf(encryptedEmail, encryptedPassword)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Usuário Logado com Sucesso")

                    var name = jsonResponse.getString("name")
                    var userName = jsonResponse.getString("user_name")

                    Log.d(TAG, "Dados criptografados recebidos do servidor -> name:$name, username:$userName")

                    name = decryptBlowfish(ServerSharedKey.value, name)
                    userName = decryptBlowfish(ServerSharedKey.value, userName)

                    Log.d(TAG, "Dados descriptografados recebidos do servidor -> name:$name, username:$userName")

                    onReponse(true, name, userName)
                } else {
                    Log.d(TAG, "Falha ao Logar usuário")
                    onReponse(false, "", "")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun listUsers(encryptedUsername: String){
        socket.emit("list-users", arrayOf(encryptedUsername)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Sucesso ao obter usuários")

                    val users = jsonResponse.getJSONArray("list")

                    Log.d(TAG, users.toString())

                    for (i in 0 ..< users.length()) {
                        val user = users.getJSONObject(i)

                        var name = user.getString("name")
                        var userName = user.getString("user_name")
                        var email = user.getString("email")

                        Log.d(TAG,"Dados de usuário criptografados recebidos do servidor -> name: $name, username: $userName, email: $email")

                        name = decryptBlowfish(ServerSharedKey.value, name)
                        userName = decryptBlowfish(ServerSharedKey.value, userName)
                        email = decryptBlowfish(ServerSharedKey.value, email)

                        Log.d(TAG,"Dados de usuário descriptografados recebidos do servidor -> name: $name, username: $userName, email: $email")

                        database.userDao().insert(
                            User(
                                name = name,
                                username = userName,
                                email = email
                            )
                        )
                    }
                    Log.d(TAG, "Usuários salvos com sucesso")
                } else {
                    Log.d(TAG, "Falha ao obter usuários")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun requestFriend(encryptedFriend1: String, encryptedFriend2: String, encryptedP: String, encryptedG: String, encryptedPublicKey: String, onSucess: () -> Unit) {
        socket.emit("friend-request", arrayOf(encryptedFriend1, encryptedFriend2, encryptedP, encryptedG, encryptedPublicKey)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Solicitação de amizade enviada com sucesse")

                    onSucess()
                } else {
                    Log.d(TAG, "Falha ao enviar solicitação de amizade")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun acceptFriendRequest(encryptedFriend1: String, encryptedFriend2: String, encryptedPublicKey: String, onSucess: () -> Unit){
            socket.emit("accept-friend", arrayOf(encryptedFriend1, encryptedFriend2, encryptedPublicKey)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Usuário aceito como amigo com sucesso")
                    onSucess()
                } else {
                    Log.d(TAG, "Falha ao aceitar usuário como amigo")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun declineFriendRequest(encryptedFriend1: String, encryptedFriend2: String, onSucess: () -> Unit){
        socket.emit("accept-friend", arrayOf(encryptedFriend1, encryptedFriend2)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Usuário aceito como amigo com sucesso")
                    onSucess()
                } else {
                    Log.d(TAG, "Falha ao aceitar usuário como amigo")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }

    fun sendMessage(encryptedSender: String, encryptedReceiver: String, encryptedTimestamp: String, encryptedContent: String, onSucess: () -> Unit ) {
        socket.emit("send-message", arrayOf(encryptedSender, encryptedReceiver, encryptedTimestamp, encryptedContent)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Mensagem enviada com sucesso")
                    onSucess()
                } else {
                    Log.d(TAG, "Falha ao enviar mensagem")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }


    fun getOfflineData(encryptedUsername: String) {
        socket.emit("online-loged", arrayOf(encryptedUsername)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                Log.d(TAG, jsonResponse.toString())
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Sucesso ao obter dados recebidos enquanto offline")
                    val offlineReceivedMessages = jsonResponse.getJSONArray("offlineReceivedMessages")
                    val offlineFriendRequests = jsonResponse.getJSONArray("offlineFriendRequests")
                    val offlineAcceptedRequests = jsonResponse.getJSONArray("offlineAcceptedRequests")

                } else {
                    Log.d(TAG, "Falha ao obter dados recebidos enquanto offline")
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }
}

