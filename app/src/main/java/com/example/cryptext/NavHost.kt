package com.example.cryptext

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptext.ui.screen.ConversaPage
import com.example.cryptext.ui.screen.ConversasPage
import com.example.cryptext.ui.screen.FriendRequesPage
import com.example.cryptext.ui.screen.FriendsPage
import com.example.cryptext.ui.screen.LoginPage
import com.example.cryptext.ui.screen.ProfilePage
import com.example.cryptext.ui.screen.SingUpPage

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginPage(
                navController
            )
        }

        composable("signup") {
            SingUpPage(
                navController
            )
        }

        composable("conversas") {
            ConversasPage(
                navController
            )
        }

        composable("conversa/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ConversaPage(
                navController,
                username
            )
        }

        composable("friends") {
            FriendsPage(
                navController
            )
        }

        composable("friendsRequest") {
            FriendRequesPage(
                navController
            )
        }

        composable("profile/{myproflie}/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val myprofile = backStackEntry.arguments?.getBoolean("username") ?: true

            ProfilePage(
                navController,
                myProfile = myprofile,
                username = username
            )
        }
    }
}