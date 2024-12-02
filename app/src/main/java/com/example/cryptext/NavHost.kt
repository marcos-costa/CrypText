package com.example.cryptext

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.UserDataRepository
import com.example.cryptext.ui.screen.ConversaPage
import com.example.cryptext.ui.screen.ConversasPage
import com.example.cryptext.ui.screen.FriendRequesPage
import com.example.cryptext.ui.screen.FriendsPage
import com.example.cryptext.ui.screen.LoginPage
import com.example.cryptext.ui.screen.ProfilePage
import com.example.cryptext.ui.screen.SingUpPage
import com.example.cryptext.ui.viewmodel.MainViewModel

@Composable
fun MyApp(
    database: AppDatabase,
    userDataRepository: UserDataRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login/a/a"
    ) {

        val viewModel = MainViewModel(
            database,
            userDataRepository
        )

        composable(
            route = "login/{email}/{senha}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                },
                navArgument("senha") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val senha = backStackEntry.arguments?.getString("senha") ?: ""

            LoginPage(
                navController,
                viewModel,
                email = email,
                senha = senha
            )
        }

        composable("signup") {
            SingUpPage(
                navController,
                viewModel,
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
            route = "myProfile"
        ){
            ProfilePage(
                navController,
                viewModel,
                myProfile = true,
                username = ""
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