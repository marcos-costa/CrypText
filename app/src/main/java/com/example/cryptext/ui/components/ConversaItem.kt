package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.data.ui.Message
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun ConversaItem(
    message: Message,
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
                text = message.friendName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text (
                text = message.message,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(200.dp)
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Column (
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 15.dp, end = 15.dp)
        ) {
            Text(
                text = message.date,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = message.hour,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConversaItemPreview() {
    CrypTextTheme {
        ConversaItem(
            message = Message(
                friendName = "Marcos",
                message = "Olá bom lakjsdlçkjasçlkdjfçlaksjdfçlkajsdçlkfjaçlskdjflaksjdf",
                date = "15/10",
                hour = "17:34",
                received = true
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}