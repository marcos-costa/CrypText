package com.example.cryptext.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.data.ui.Message

@Composable
fun Message (
    message: Message,
    modifier: Modifier = Modifier
) {
    Row (modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = if (message.received) Arrangement.Start else Arrangement.End
    ){
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = if (message.received) Alignment.Start else Alignment.End
        ) {
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = if (message.received) 0f else 48f,
                            bottomEnd = if (message.received) 48f else 0f
                        )
                    )
                    .background(if (message.received) Color(0xFFD7D7D7) else Color(0xFFB6A3E3))
                    .padding(15.dp)
            )
            Row(
                horizontalArrangement = if (message.received) Arrangement.Start else Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message.date,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = message.hour,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessagePreviewReceived() {
    Message(
        Message(
            friendName = "Marcos",
            message = "Lorem ipsum dolor",
            date = "12/02",
            hour = "12:50",
            received = true
            ),
        modifier = Modifier.padding(15.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessagePreviewSend() {
    Message(
        Message(
            friendName = "Marcos",
            message = "Lorem ipsum dolor sit amet. Sit quis impedit aut accusantium quisquam est iure eaque qui eius dicta.",
            date = "12/02",
            hour = "12:50",
            received = false
        ),
        modifier = Modifier.padding(15.dp)
    )
}