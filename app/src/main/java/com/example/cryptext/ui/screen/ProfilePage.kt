package com.example.cryptext.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptext.R
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.ui.components.InputText
import com.example.cryptext.ui.components.LineSepator
import com.example.cryptext.ui.components.TopAppBar
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun ProfilePage(
    navController: NavHostController,
    viewModel: MainViewModel,
    myProfile: Boolean = false,
    username: String
) {
    viewModel.getFriend(username)

    val friend = viewModel.friend.collectAsState(initial = Friend(id = "0",name = "Desconhecido", email = "none", username = "@none", sharedKey = "none", ))

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        TopAppBar(
            modifier = Modifier.fillMaxWidth()
        )
        LineSepator(
            icon = R.drawable.profile_icon,
            text = if (myProfile) "Meu Perfil" else "Amigo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        Image(
            painter = painterResource(R.drawable.default_profile_picture),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        InputText(
            icon = R.drawable.user_icon,
            label = "Nome",
            placeHolderText = "",
            password = false,
            enabled = false,
            value = friend.value.name,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        InputText(
            icon = R.drawable.username_icon,
            label = "Nome de Usuário",
            placeHolderText = "",
            password = false,
            enabled = false,
            value = friend.value.username,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        InputText(
            icon = R.drawable.email_icon,
            label = "E-mail",
            placeHolderText = "",
            password = false,
            enabled = false,
            value = friend.value.email,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        Spacer (
            modifier = Modifier.height(70.dp)
        )
        if (!myProfile){
            Box (
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    colors = ButtonColors(
                        containerColor = Color(0xFFF4F1FF),
                        contentColor = Color(0xFF65558F),
                        disabledContainerColor = Color(0xFFF4F1FF),
                        disabledContentColor = Color(0xFF65558F)
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(15.dp),
                    onClick = {
                        val username = friend.value.username
                        navController.navigate("conversa/$username")}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.message_icon),
                        contentDescription = null
                    )
                    Text(
                        text = "Mensagem",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}
