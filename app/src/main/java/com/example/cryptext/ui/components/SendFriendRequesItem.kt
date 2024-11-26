package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.example.cryptext.data.domain.UserUI
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun SendFriendRequestItem(
    userUI: UserUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFFF4F1FF)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.default_profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(start = 15.dp)
        )
        Column (
            modifier = Modifier.padding(start = 10.dp)
        ){
            Text (
                text = userUI.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text (
                text = userUI.username,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Column (
            modifier = Modifier
                .padding(end = 15.dp)
        ) {
            Button (
                colors = ButtonColors(
                    containerColor = Color(0xFF65558F),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFD7D7D7),
                    disabledContentColor = Color(0xFF736B6B),
                ),
                onClick = onClick
            ) {
                Text(
                    text = "Solicitar",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SendFriendRequestItemPreview() {
    CrypTextTheme {
        SendFriendRequestItem(
            userUI = UserUI (
                name = "Marcos",
                username = "@marcos",
                email = "marcos@mail.com"
            ),
            onClick = {}
        )
    }
}