package com.example.androidbooklist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidbooklist.screens.BookDetailScreen
import com.example.androidbooklist.screens.MainScreen
import com.example.androidbooklist.screens.ReadingListScreen
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route) {
            composable(route = AppScreens.MainScreen.route) {
                MainScreen(navController)
            }
            composable(
                route = "book_detail_screen/{isbn}",
                arguments = listOf(navArgument("isbn") {type = NavType.StringType})
            ) { backStackEntry ->
                val isbn = backStackEntry.arguments?.getString("isbn")

                BookDetailScreen(isbn.toString(), navController) {
                    navController.popBackStack()
                }
            }
            composable(AppScreens.ReadingListScreen.route) {
                ReadingListScreen(
                    onBookClicked = {
                        navController.navigate(route = "book_detail_screen/${it}")
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
    }
}
