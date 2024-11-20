package com.example.cryptext.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
        ){
        Image(
            painter = painterResource(id = R.drawable.ellipse_login_1),
            contentDescription  = "null",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.ellipse_login_2),
            contentScale = ContentScale.Crop,
            contentDescription  = null,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "CrypText",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(40.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier.size(100.dp),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginHearderPreview() {
    CrypTextTheme {
        LoginHeader()
    }
}