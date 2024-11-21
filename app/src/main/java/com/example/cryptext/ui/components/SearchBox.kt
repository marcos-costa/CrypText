package com.example.cryptext.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptext.R
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun SearchBar(
    icon: Int,
    placeHolderText: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE1EAF0)),
            trailingIcon = {
                IconButton (
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE1EAF0)),
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    ),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search_icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeHolderText
                )
            },
            shape = RoundedCornerShape(30),
            singleLine = true,
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextBarPreviewPreview() {
    CrypTextTheme {
        var value = ""

        SearchBar(
            icon = R.drawable.email_icon,
            placeHolderText = "Pesquise um Usuário",
            value = value,
            onValueChange = {value = it}
        )
    }
}