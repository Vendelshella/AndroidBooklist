package com.example.androidbooklist.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavController
import com.example.androidbooklist.data.Library
import com.example.androidbooklist.data.LibraryApp


// TODO: Crear la pantalla de la lista de lectura

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen(
    //navController: NavController,
    navigateBack: () -> Unit
) {

    var libraryList by remember { mutableStateOf(emptyList<Library>()) }

    LaunchedEffect(Unit) {
        libraryList = LibraryApp.db.libraryDao().getAllLibs()
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
    )  {
        Column (
            modifier = Modifier.padding(it)
        ){
            if (libraryList.isNotEmpty()) {
                // TODO:
                //  reescribir PrintBookCard (pasar atributos en vez de objeto)
                //  en una LazyVerticalGrid
                LazyColumn {
                    items(libraryList.size) { index ->
                        Text(text = libraryList[index].title)
                    }
                }
            } else {
                Text("No tienes libros guardados en tu biblioteca")
            }
        }
    }
}