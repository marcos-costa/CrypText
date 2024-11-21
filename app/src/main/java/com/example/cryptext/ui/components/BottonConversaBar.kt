package com.example.cryptext.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun BottonConversaBar(
    text: String,
    placeHolderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFF7F7FC)),
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(30),
            value = text,
            onValueChange = onValueChange,
        )
        IconButton (
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE1EAF0))
                .padding(start = 10.dp),
            colors = IconButtonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF65558F),
                disabledContainerColor = Color.White,
                disabledContentColor = Color(0xFF65558F)
            ),
            onClick = {}
        ) {
            Icon(
                painter = painterResource(R.drawable.send_icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottonConversaBarPreview() {
    CrypTextTheme {
        BottonConversaBar(
            text = "",
            placeHolderText = "Digite sua mensagem",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
    }
}