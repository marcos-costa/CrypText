package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.Row
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
import com.example.cryptext.data.domain.UserUI
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun SendFriendRequestItemList (
    userUIS: List<UserUI>,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
    ) {
        LazyColumn {
            if (userUIS.isNotEmpty()){
                items(userUIS) { user ->
                    SendFriendRequestItem(
                        userUI = user,
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )
                }
            } else {
                item {
                    Text (
                        text = "Nenhuma solicitação pendente",
                        style = MaterialTheme.typography.bodyLarge
                        )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SendFriendRequestItemListPreveiw() {
    val userUIS = listOf(
        UserUI(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        ),
        UserUI(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        ),
        UserUI(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        )
    )
    CrypTextTheme {
        SendFriendRequestItemList(
            userUIS = userUIS,
        )
    }
}

