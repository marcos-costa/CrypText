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
import com.example.cryptext.R
import com.example.cryptext.ui.components.BottomAppBar
import com.example.cryptext.ui.components.LineSepator
import com.example.cryptext.ui.components.SearchBar
import com.example.cryptext.ui.components.SendFriendRequestItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun FriendRequesPage(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    viewModel.listUsers()

    var unreadMessages = viewModel.unreadMessagesCount.collectAsState(initial = 0)
    var friendsSolicitations = viewModel.pendingSolicitationsCount.collectAsState(initial = 0)

    var usersList = viewModel.usersList.collectAsState(initial = emptyList())

    var value by remember { mutableStateOf("") }
    Scaffold (
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                )
                LineSepator(
                    icon = R.drawable.friends_icon,
                    text = "Solicitar amizades",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
                SearchBar(
                    icon = R.drawable.search_icon,
                    placeHolderText = "Buscar novos amigos",
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        },
        content = { paddingValues ->
            SendFriendRequestItemList(
                users = usersList.value,
                onClick = { viewModel.requestFriend(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(15.dp)
            )
        },
        bottomBar = {
            BottomAppBar(
                unreadMessages = unreadMessages.value,
                friendRequests = friendsSolicitations.value,
                onClickConversas = { navHostController.navigate("conversas") },
                onClickFriends = { navHostController.navigate("friends") },
                onClickProfile = { navHostController.navigate("profile/true/") },
                modifier = Modifier.fillMaxWidth()
            )
        },
    )
}