package com.example.cryptext

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.ui.screen.ConversaPage
import com.example.cryptext.ui.screen.ConversasPage
import com.example.cryptext.ui.screen.FriendRequesPage
import com.example.cryptext.ui.screen.FriendsPage
import com.example.cryptext.ui.screen.LoginPage
import com.example.cryptext.ui.screen.ProfilePage
import com.example.cryptext.ui.screen.SingUpPage
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun MyApp(database: AppDatabase) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        val viewModel = MainViewModel(database)
        composable("login") {
            LoginPage(
                navController
            )
        }

        composable("signup") {
            SingUpPage(
                navController,
            )
        }

        composable("conversas") {
            ConversasPage(
                navController,
                viewModel
            )
        }

        composable(
            route = "conversa/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""

            ConversaPage(
                navController,
                viewModel,
                username
            )
        }

        composable("friends") {
            FriendsPage(
                navController,
                viewModel
            )
        }

        composable("friendsRequest") {
            FriendRequesPage(
                navController,
                viewModel
            )
        }

        composable(
            route = "friendProfile/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""

            ProfilePage(
                navController,
                viewModel,
                myProfile = false,
                username = username
            )
        }
    }
}