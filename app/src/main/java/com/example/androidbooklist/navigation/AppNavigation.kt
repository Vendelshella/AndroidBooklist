package com.example.androidbooklist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidbooklist.screens.BookDetailScreen
import com.example.androidbooklist.screens.MainScreen
import com.example.androidbooklist.screens.ReadingListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route) {
            composable(route = AppScreens.MainScreen.route) {
                MainScreen(navController)
            }
            composable(AppScreens.BookDetailScreen.route) {
                BookDetailScreen(navController) {
                    navController.popBackStack()
                }
            }
            composable(AppScreens.ReadingListScreen.route) {
                ReadingListScreen(navController) {
                    navController.popBackStack()
                }
            }
    }
}
