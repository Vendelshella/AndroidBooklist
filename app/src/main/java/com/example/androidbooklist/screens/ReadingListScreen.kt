package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidbooklist.data.Library
import com.example.androidbooklist.data.LibraryApp
import com.example.androidbooklist.utils.PrintBookCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Crear la pantalla de la lista de lectura y traer los datos de la BD

data class ReadingListState(
    val books: List<Library> = emptyList(),
    val isLoading: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen(
    onBookClicked: (String) -> Unit,
    navigateBack: () -> Unit
) {
    var state by remember { mutableStateOf(ReadingListState()) }

    LaunchedEffect(Unit) {
        delay(2000)
        state = state.copy(
            books = LibraryApp.db.libraryDao().getAllLibs(),
            isLoading = false
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Mi lista de lectura")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.books.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "No hay libros agregados")
                    }
                }
                else -> {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(state.books) {
                            PrintBookCard(
                                name = it.title,
                                imageUrl = it.cover,
                                onClick = {
                                    onBookClicked(it.ISBN)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

