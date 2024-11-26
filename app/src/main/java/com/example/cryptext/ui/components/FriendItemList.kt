package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.R
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.User
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun FriendItemList(
    friendRequests: List<User>,
    friends: List<Friend>,
    navHostController: NavHostController,
    viewModel: MainViewModel,
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
            items(friendRequests) { user ->
                FriendRequestItem(
                    user = user,
                    onAccept = {viewModel.acceptFriend(user)},
                    onDecline = {viewModel.declineFriend(user)},
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
                    friend = friend,
                    onClick = {
                        val username = friend.username
                        navHostController.navigate("friendProfile/$username")
                    },
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
