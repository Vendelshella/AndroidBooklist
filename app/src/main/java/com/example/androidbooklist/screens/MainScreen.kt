package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.androidbooklist.utils.MakeGrid
import com.example.androidbooklist.data.AssetsBooksDataSource
import com.example.androidbooklist.data.BooksRepository


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController) {

    // MUY IMPORTANTE: usar remember para que no haga falta volver a leer el JSON con cada cambio.
    val context = LocalContext.current
    val repository = remember { BooksRepository.create(context.applicationContext) }

    val booksList = remember { repository.getAll() }

    val pagesFilter = remember { booksList.map { it.pages }.sorted() }
    var selectedPageNumber by rememberSaveable { mutableIntStateOf(pagesFilter.last()) }

    val genresFilter = remember { booksList.map { it.genre }.distinct() }
    var selectedGenre by rememberSaveable { mutableStateOf("") }

    // ProduceState permite hacer operaciones asíncronas en base al cambio de las keys
    // Es como un LaunchedEffect pero con un valor de retorno.
    // OJO: remember siempre se ejecuta en el hilo principal; LaunchedEffect y produceState
    // se ejecutan en un hilo separado.
    val filteredBooks by produceState(
        initialValue = booksList,
        key1 = selectedGenre,
        key2 = selectedPageNumber
    ) {
        value = booksList.filter {
            if (selectedGenre.isEmpty())
                true
            else
                it.genre == selectedGenre
        }.filter { it.pages <= selectedPageNumber }
    }

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