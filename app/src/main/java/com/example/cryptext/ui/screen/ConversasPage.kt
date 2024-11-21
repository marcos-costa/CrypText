package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.cryptext.data.ui.Message
import com.example.cryptext.ui.components.BottomAppBar
import com.example.cryptext.ui.components.ConversaItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun ConversasPage(
    navHostController: NavHostController
) {
    var unreadMessages by remember { mutableStateOf("0") }
    var friendsSolicitations by remember { mutableStateOf("0") }
    var messages by remember { mutableStateOf<List<Message>>(emptyList())}

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
            ConversaItemList(
                navHostController = navHostController,
                messages = messages,
                modifier = Modifier.padding(paddingValues)
            )
        }

    )
}
