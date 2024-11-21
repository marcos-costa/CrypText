package com.example.cryptext.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.R
import com.example.cryptext.ui.components.InputText
import com.example.cryptext.ui.components.LineSepator
import com.example.cryptext.ui.components.LoginButton
import com.example.cryptext.ui.components.SingUpHeader
import com.example.cryptext.ui.theme.CrypTextTheme

@Composable
fun SingUpPage(
    navController: NavHostController
){

    var name by remember { mutableStateOf("")}
    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SingUpHeader()
        }
        LineSepator(
            icon = R.drawable.new_user_icon,
            text = "Registro de Usuário",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        InputText(
            icon = R.drawable.user_icon,
            label = "Nome",
            placeHolderText = "Usuário",
            password = false,
            value = name,
            onValueChange = { name = it},
            modifier = Modifier.padding(15.dp)
        )
        InputText(
            icon = R.drawable.username_icon,
            label = "Nome de usuário",
            placeHolderText = "@user_name",
            password = false,
            value = username,
            onValueChange = { username = it},
            modifier = Modifier.padding(15.dp)
        )
        InputText(
            icon = R.drawable.email_icon,
            label = "E-mail",
            placeHolderText = "teste@mail.com",
            password = false,
            value = email,
            onValueChange = { email = it},
            modifier = Modifier.padding(15.dp)
        )
        InputText(
            icon = R.drawable.password_icon,
            label = "Senha",
            placeHolderText = "*******",
            password = true,
            value = senha,
            onValueChange = { senha = it},
            modifier = Modifier.padding(15.dp)
        )
        LoginButton(
            text = "Confirmar",
            onClick = { navController.navigate("conversas") }
        )
    }
}
