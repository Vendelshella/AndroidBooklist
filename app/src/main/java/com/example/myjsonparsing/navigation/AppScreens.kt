package com.example.myjsonparsing.navigation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("main_screen")
    object BookDetailScreen: AppScreens("book_detail_screen")
    object ReadingListScreen: AppScreens("reading_list_screen")
}