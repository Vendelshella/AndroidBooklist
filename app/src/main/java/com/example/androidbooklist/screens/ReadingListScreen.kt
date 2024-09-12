package com.example.androidbooklist.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidbooklist.data.Library
import com.example.androidbooklist.data.LibraryApp
import com.example.androidbooklist.utils.PrintBookCard

// TODO: animación mientras carga la información de la BD por si tarda tiempo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen(
    navController: NavController,
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
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier.fillMaxSize().padding(top = 14.dp)
                ) {
                    items(libraryList.size) { index ->
                        PrintBookCard(
                            title = libraryList[index].title,
                            isbn = libraryList[index].ISBN,
                            cover = libraryList[index].cover,
                            navController = navController
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No tienes libros guardados en tu biblioteca",
                    )
                }
            }
        }
    }
}