package com.example.cryptext.backendIntegration

import android.util.Log
import com.example.cryptext.data.ServerSharedKey
import com.example.cryptext.encrypt.DiffieHellman
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.math.BigInteger

object SocketHandler {

    private var TAG = "Backend Integration"

    lateinit private var socket: Socket

    fun connect(serverUrl: String) {
        socket = IO.socket(serverUrl)
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            Log.d(TAG, "Connected to Server")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d(TAG, "Disconnected to Server")
        }
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

                    ServerSharedKey.value = sharedServerKey
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

    fun login(encryptedEmail: String, encryptedPassword: String, onReponse: (Boolean) -> Unit){
        socket.emit("login", arrayOf(encryptedEmail, encryptedPassword)) { response ->
            if (response.isNotEmpty()) {
                val jsonResponse = response[0] as JSONObject
                if (jsonResponse.getBoolean("success")) {
                    Log.d(TAG, "Usuário Logado com Sucesso")
                    onReponse(true)
                } else {
                    Log.d(TAG, "Falha ao Logar usuário")
                    onReponse(false)
                }
            } else {
                Log.d(TAG, "Nenhuma resposta recebida do servidor.")
            }
        }
    }
}

