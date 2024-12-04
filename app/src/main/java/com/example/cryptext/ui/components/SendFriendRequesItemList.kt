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
import com.example.cryptext.data.entity.User
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun SendFriendRequestItemList (
    users: List<User>,
    onClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
    ) {
        LazyColumn {
            if (users.isNotEmpty()){
                items(users) { user ->
                    SendFriendRequestItem(
                        user = user,
                        onClick = { onClick( user ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )
                }
            } else {
                item {
                    Text (
                        text = "Nenhum usuário encontrado",
                        style = MaterialTheme.typography.bodyLarge
                        )
                }
            }
        }
    }
}


