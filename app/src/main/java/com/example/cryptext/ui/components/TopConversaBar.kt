package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.data.entity.Friend

@Composable
fun TopConversaBar (
    friend: Friend,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
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
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text (
                text = friend.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text (
                text = friend.status,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}