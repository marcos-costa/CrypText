package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cryptext.R
import com.example.cryptext.SocketHandler
import com.example.cryptext.ui.components.InputText
import com.example.cryptext.ui.components.LineSepator
import com.example.cryptext.ui.components.LoginButton
import com.example.cryptext.ui.components.LoginHeader
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun LoginPage(
    navController: NavHostController,
    email: String,
    senha: String
) {
    
    var email by remember { mutableStateOf(email)}
    var senha by remember { mutableStateOf(senha)}
    val socket = SocketHandler.getSocket()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        LoginHeader(
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Entre em sua Conta",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        LineSepator(
            icon = R.drawable.login_icon,
            text = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp))
        InputText(
            icon = R.drawable.email_icon,
            label = "E-mail",
            placeHolderText = "teste@mail.com",
            value = email,
            onValueChange = {email = it},
            password = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        InputText(
            icon = R.drawable.password_icon,
            label = "Senha",
            placeHolderText = "**********",
            value = senha,
            onValueChange = {senha = it},
            password = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        LoginButton(
            text = "Confirmar",
            onClick = {navController.navigate("conversas")},
            modifier = Modifier.padding(top = 15.dp)
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "Não Tem Conta?",
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(
                onClick = { navController.navigate("signup") }
            ) {
                Text(
                    text = "Faça Seu Cadastro?",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
