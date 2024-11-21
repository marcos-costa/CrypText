package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.cryptext.data.ui.User
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun FriendItem(
    user: User,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFFE5E9FF)),
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
                text = user.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text (
                text = user.username,
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
            Text (
                text = user.status,
                modifier = Modifier
                    .background(if (user.status == "Online") {
                        Color(0xFF83CED0)
                    } else {
                        Color(0xFFF1B580)
                    }, shape = RoundedCornerShape(12.dp))
                    .padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileItemPreview() {
    CrypTextTheme {
        FriendItem(
            user = User (
                name = "Marcos",
                username = "@marcos",
                email = "marcos@mail.com"
            )
        )
    }
}