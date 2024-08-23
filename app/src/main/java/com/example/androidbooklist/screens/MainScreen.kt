package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.androidbooklist.utils.MakeGrid
import com.example.androidbooklist.utils.filterBooks
import com.example.androidbooklist.BooksData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (navController: NavController) {

    val booksData = BooksData()
    val booksList = booksData.readBooks(context = LocalContext.current, "books.json")

    val pagesFilter = remember { booksList.map { it.book.pages }.sorted() }
    var selectedPageNumber by remember { mutableIntStateOf(pagesFilter.last()) }

    val genresFilter = remember { booksList.map { it.book.genre }.distinct() }
    var selectedGenre by remember { mutableStateOf("") }

    val filteredBooks = filterBooks(
        books = booksList,
        genre = selectedGenre,
        page = selectedPageNumber
    )

    MakeGrid(
        books = filteredBooks,
        selectedGenre = selectedGenre,
        genresFilter = genresFilter,
        onGenreSelected = { selectedGenre = it },
        selectedPage = selectedPageNumber,
        pagesFilter = pagesFilter,
        onPageSelected = { selectedPageNumber = it },
        navController = navController
    )
}