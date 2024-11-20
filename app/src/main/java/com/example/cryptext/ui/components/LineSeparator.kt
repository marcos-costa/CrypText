package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
fun LineSepator(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(icon),
                modifier = Modifier.size(25.dp),
                contentDescription = null
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = 15.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp),
            color = Color(0xFF5A0FC8)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LineSeparatorLoginPreview() {
    CrypTextTheme {
        LineSepator(R.drawable.login_icon, "Login")
    }
}