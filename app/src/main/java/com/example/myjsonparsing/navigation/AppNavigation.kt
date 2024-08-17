package com.example.myjsonparsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myjsonparsing.screens.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route) {
            composable(route = AppScreens.MainScreen.route) {
                MainScreen(navController)
            }
            composable(route = AppScreens.BookDetailScreen.route) {
                // BookDetailScreen(navController)
            }
            composable(route = AppScreens.ReadingListScreen.route) {
                // ReadingListScreen(navController)
            }
    }
}
