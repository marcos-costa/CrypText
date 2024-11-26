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
import androidx.compose.ui.unit.dp
import com.example.cryptext.data.entity.Message
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MessageBuble (
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
                text = message.content,
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
                val formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm")
                    .withZone(ZoneId.systemDefault())
                Text(
                    text = formatter.format(Instant.ofEpochMilli(message.timestamp)),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
