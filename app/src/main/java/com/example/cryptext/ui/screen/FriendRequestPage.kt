package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.ui.components.LineSepator
import com.example.cryptext.ui.components.SearchBar
import com.example.cryptext.ui.components.SendFriendRequestItemList
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun FriendRequesPage() {
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
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        SendFriendRequestItemList(
            users = emptyList(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FriendRequesPagePreview(){
    CrypTextTheme {
        FriendRequesPage()
    }
}