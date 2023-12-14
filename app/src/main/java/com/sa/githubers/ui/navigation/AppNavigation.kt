package com.sa.githubers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sa.githubers.ui.screens.DetailsScreen
import com.sa.githubers.ui.screens.UsersScreen

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            UsersScreen({ login ->
                navController.navigate("${Routes.DETAILS_SCREEN}/$login")
            })
        }
        composable(
            "${Routes.DETAILS_SCREEN}/{${Routes.DETAILS_SCREEN_KEY}}",
            arguments = listOf(
                navArgument(Routes.DETAILS_SCREEN_KEY) {
                    type = NavType.StringType
                }
            )
        ) { DetailsScreen() }
    }
}