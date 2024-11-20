package com.example.cryptext.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptext.R
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun InputText(
    icon: Int,
    label: String,
    placeHolderText: String,
    password: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = placeHolderText
                )
            },
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputTextPreview() {
    CrypTextTheme {
        var value = ""

        InputText(
            icon = R.drawable.email_icon,
            label = "E-mail",
            placeHolderText = "teste@mail.com",
            value = value,
            password = false,
            onValueChange = {value = it}
        )
    }
}