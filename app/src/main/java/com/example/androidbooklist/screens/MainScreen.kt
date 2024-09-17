package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.androidbooklist.utils.MakeGrid
import com.example.androidbooklist.utils.filterBooks
import com.example.androidbooklist.data.BooksDataSource


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController) {

    // MUY IMPORTANTE: usar remember para que no haga falta volver a leer el JSON con cada cambio.
    val context = LocalContext.current
    val booksList = remember { BooksDataSource.readBooks(context = context) }

    val pagesFilter = remember { booksList.map { it.book.pages }.sorted() }
    var selectedPageNumber by rememberSaveable { mutableIntStateOf(pagesFilter.last()) }

    val genresFilter = remember { booksList.map { it.book.genre }.distinct() }
    var selectedGenre by rememberSaveable { mutableStateOf("") }

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