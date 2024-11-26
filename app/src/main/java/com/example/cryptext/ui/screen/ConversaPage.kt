package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.ui.components.BottonConversaBar
import com.example.cryptext.ui.components.MessageList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.components.TopConversaBar
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun ConversaPage(
    navHostController: NavHostController,
    viewModel: MainViewModel,
    username: String
) {
    viewModel.getFriend(username)
    viewModel.getFriendMessage(username)

    val friend = viewModel.friend.collectAsState(initial = Friend(id = "0",name = "Desconhecido", email = "none", username = "@none", sharedKey = "none", ))
    val messages = viewModel.friendMessages.collectAsState(initial = emptyList())

    var text by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                )
                TopConversaBar(
                    friend = friend.value,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            BottonConversaBar(
                text = text,
                placeHolderText = "Digite sua mensagem",
                onValueChange = {text = it},
                onSend = {
                    viewModel.sendMessage(text, friend.value.username)
                    text = ""
                         },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        },
        content = { paddingValues ->
            MessageList(
                messages = messages.value,
                modifier = Modifier
                    .padding(paddingValues)
            )
        },

    )
}