package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.data.ui.Message
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun MessageList(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    if (messages.isEmpty()){
        Column (
            modifier = modifier
        ) {
            Text(
                text = "Nenhuma Mensagem",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(15.dp)
            )
        }
    } else {
        LazyColumn (
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messages) { message ->
                Message(
                    message = message,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessageListPreview(){
    val messages = listOf(
        Message(
            friendName = "Marcos",
            message = "Olá bom dia",
            date = "15/10",
            hour = "17:34",
            received = true
        ),
        Message(
            friendName = "Marcos",
            message = "Olá bom dia",
            date = "15/10",
            hour = "17:34",
            received = false
        ),
        Message(
            friendName = "Marcos",
            message = "Olá bom dia",
            date = "15/10",
            hour = "17:34",
            received = true
        )
    )
    CrypTextTheme {
        MessageList(
            messages = messages,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
