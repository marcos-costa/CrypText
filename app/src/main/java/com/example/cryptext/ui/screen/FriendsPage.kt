package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.cryptext.R
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.components.BottomAppBar
import com.example.cryptext.ui.components.FriendItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun FriendsPage(
    navHostController: NavHostController,
) {
    val friends = emptyList<User>()
    val friendRequest = emptyList<User>()

    var unreadMessages by remember { mutableStateOf("0") }
    var friendsSolicitations by remember { mutableStateOf("0") }
    
    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBar(
                unreadMessages = unreadMessages,
                friendRequests = friendsSolicitations,
                onClickConversas = { navHostController.navigate("conversas") },
                onClickFriends = { navHostController.navigate("friends") },
                onClickProfile = { navHostController.navigate("profile/true/") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            FriendItemList(
                friendRequests = friendRequest,
                friends = friends,
                modifier = Modifier.padding(paddingValues)
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = { navHostController.navigate("friendsRequest")},
                colors = IconButtonColors(
                    containerColor = Color(0xFF6750A4),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF6750A4),
                    disabledContainerColor = Color.White,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.accept_icon),
                    contentDescription = null
                )
            }
        }
    )
}