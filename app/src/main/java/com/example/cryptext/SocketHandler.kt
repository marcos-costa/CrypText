package com.example.cryptext

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
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
}