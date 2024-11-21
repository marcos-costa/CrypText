package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.components.BottomAppBar
import com.example.cryptext.ui.components.FriendItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun FriendsPage(
    friends: List<User>,
    friendRequest: List<User>
) {
    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBar(
                unreadMessages = "3",
                friendRequests = "2",
                onClickFriends = {},
                onClickProfile = {},
                onClickConversas = {},
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            FriendItemList(
                friendRequests = friendRequest,
                friends = friends,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FriendsPagePreview() {
    CrypTextTheme {
        FriendsPage(
            emptyList(), emptyList()
        )
    }
}