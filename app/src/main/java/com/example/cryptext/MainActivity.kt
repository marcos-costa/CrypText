package com.example.cryptext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cryptext.ui.screen.SingUpPage
import com.example.cryptext.ui.theme.CrypTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val mSocket = SocketHandler.getSocket()

        // diffie hellman
        mSocket.emit("counter")

        setContent {
            CrypTextTheme {
                MyApp()
            }
        }
    }
}

