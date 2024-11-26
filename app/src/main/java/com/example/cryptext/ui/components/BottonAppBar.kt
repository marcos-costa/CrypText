package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBarState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun BottomAppBar(
    unreadMessages: Int,
    friendRequests: Int,
    onClickConversas: () -> Unit,
    onClickFriends: () -> Unit,
    onClickProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.background(Color(0xFFE4E8F8)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box (
            modifier = Modifier.clickable(onClick = onClickConversas)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.conversas_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Conversas",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (unreadMessages != 0){
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB6A3E3))
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = unreadMessages.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Box (
            modifier = Modifier.clickable(onClick = onClickFriends)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.friends_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Amigos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (friendRequests != 0) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB6A3E3))
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = friendRequests.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Box (
            modifier = Modifier.clickable(onClick = onClickProfile)
        )  {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "Perfil",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottonAppBarPreview(){
    CrypTextTheme {
        BottomAppBar(
            unreadMessages = 5,
            friendRequests = 3,
            onClickConversas = {},
            onClickFriends = {},
            onClickProfile = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottonAppBarPreview2(){
    CrypTextTheme {
        BottomAppBar(
            unreadMessages = 0,
            friendRequests = 0,
            onClickConversas = {},
            onClickFriends = {},
            onClickProfile = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}