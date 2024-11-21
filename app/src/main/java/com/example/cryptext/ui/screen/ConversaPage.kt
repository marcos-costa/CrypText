package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.data.ui.Message
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.components.BottonConversaBar
import com.example.cryptext.ui.components.MessageList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.components.TopConversaBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun ConversaPage(
    user: User,
    messages: List<Message>
) {
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ConversaPagePreview() {
    CrypTextTheme {
        ConversaPage(
            user = User(
                name = "Marcos",
                username = "@marcos",
                email = "marcos@mail.com"
            ),
            messages = emptyList()
        )
    }
}