package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun LoginButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {
        Button (
            onClick = onClick,
            colors = ButtonColors(
                containerColor = Color(0xFF65558F),
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.White
            ),
            enabled = true
        ) {
            Text (
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    CrypTextTheme {
        LoginButton({}, "Confirmar")
    }
}