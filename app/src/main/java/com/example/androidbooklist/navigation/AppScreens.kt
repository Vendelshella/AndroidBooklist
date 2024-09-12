package com.example.androidbooklist.navigation

sealed class AppScreens(val route: String) {
    data object MainScreen: AppScreens("main_screen")
    data object BookDetailScreen: AppScreens("book_detail_screen")
    data object ReadingListScreen: AppScreens("reading_list_screen")
}