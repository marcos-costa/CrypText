package com.example.cryptext.ui.components

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
import com.example.cryptext.R
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun FriendItemList(
    friendRequests: List<User>,
    friends: List<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.padding(horizontal = 15.dp)
    ) {
        item {
            LineSepator(
                icon = R.drawable.friends_icon,
                text = "Solicitações",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)

            )
        }
        if (friendRequests.isNotEmpty()){
            items(friendRequests) { friend ->
                FriendRequestItem(
                    user = friend,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        } else {
            item {
                Text(
                    text = "Nenhuma Solicitação",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }

        item {
            LineSepator(
                icon = R.drawable.friends_icon,
                text = "Amigos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
        if (friends.isNotEmpty()) {
            items(friends) { friend ->
                FriendItem(
                    user = friend,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)

                )
            }
        }else {
            item {
                Text(
                    text = "Nenhum Amigo",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemEmptyListPreview() {
    CrypTextTheme {
        FriendItemList(
            friendRequests = emptyList<User>(),
            friends = emptyList<User>()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemListPreview() {
    val friends = listOf(
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        ),
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com",
            status = "Offline"
        ),
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        )
    )
    val friendRequests = listOf(
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        ),
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        ),
        User(
            name = "Marcos Costa",
            username = "@marcos",
            email = "marcos@mail.com"
        )
    )
    CrypTextTheme {
        FriendItemList(
            friendRequests = friendRequests,
            friends = friends
        )
    }
}