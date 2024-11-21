package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.data.ui.Message
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.components.BottonConversaBar
import com.example.cryptext.ui.components.MessageList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.components.TopConversaBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun ConversaPage(
    navHostController: NavHostController,
    username: String
) {
    var user = User(name = "Marcos Costa", username = "@marcos", email = "@mail", status = "Online")
    var messages = emptyList<Message>()

    Scaffold (
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                )
                TopConversaBar(
                    user = user,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            BottonConversaBar(
                text = "",
                placeHolderText = "Digite sua mensagem",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        },
        content = { paddingValues ->
            MessageList(
                messages = messages,
                modifier = Modifier
                    .padding(paddingValues)
            )
        },

    )
}