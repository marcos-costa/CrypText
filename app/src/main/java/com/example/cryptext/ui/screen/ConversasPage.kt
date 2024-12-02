package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cryptext.ui.components.BottomAppBar
import com.example.cryptext.ui.components.ConversaItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun ConversasPage(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    var unreadMessages = viewModel.unreadMessagesCount.collectAsState(initial = 0)
    var friendsSolicitations = viewModel.pendingSolicitationsCount.collectAsState(initial = 0)

    var lastMessages = viewModel.lastChat.collectAsState(initial = emptyList())

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBar(
                unreadMessages = unreadMessages.value,
                friendRequests = friendsSolicitations.value,
                onClickConversas = { navHostController.navigate("conversas") },
                onClickFriends = { navHostController.navigate("friends") },
                onClickProfile = { navHostController.navigate("myProfile") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            ConversaItemList(
                navHostController = navHostController,
                lastChats = lastMessages.value,
                modifier = Modifier.padding(paddingValues)
            )
        }

    )
}
