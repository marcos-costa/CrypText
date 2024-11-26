package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.data.domain.MessageUI
import com.example.cryptext.data.entity.Message
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun ConversaItemList(
    navHostController: NavHostController,
    lastChats: List<Message>,
    modifier: Modifier = Modifier
) {
    if (lastChats.isEmpty()){
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
            modifier = modifier
        ) {
            items(lastChats) { message ->
                ConversaItem(
                    onClick = {
                        val username = message.friend
                        navHostController.navigate("conversa/$username")
                              },
                    message = message,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ConversaItemListPreview() {
//
//    val messages = listOf(
//        Message(
//            friendName = "Marcos",
//            message = "Olá bom dia",
//            date = "15/10",
//            hour = "17:34",
//            received = true
//        ),
//        Message(
//            friendName = "Marcos",
//            message = "Olá bom dia",
//            date = "15/10",
//            hour = "17:34",
//            received = true
//        ),
//        Message(
//            friendName = "Marcos",
//            message = "Olá bom dia",
//            date = "15/10",
//            hour = "17:34",
//            received = true
//        )
//    )
//    CrypTextTheme {
//        ConversaItemList(
//            onClick = {},
//            messages = messages,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ConversaItemListEmptyPreview() {
//    CrypTextTheme {
//        ConversaItemList(
//            messages = emptyList(),
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}