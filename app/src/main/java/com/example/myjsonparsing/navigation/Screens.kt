package com.example.myjsonparsing.navigation

sealed class Screens(val route: String) {
    object MainScreen: Screens("main_screen")
    object BookDetailScreen: Screens("book_detail_screen")
    object ReadingListScreen: Screens("reading_list_screen")
}