package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidbooklist.data.Library
import com.example.androidbooklist.data.LibraryApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Crear la pantalla de la lista de lectura y traer los datos de la BD

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReadingListScreen(
    navController: NavController,
    navigateBack: () -> Unit
) {
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
        ReadingListDetail(navController)
    }
}

@Composable
fun ReadingListDetail(navController: NavController) {

    val app = LocalContext.current.applicationContext as LibraryApp
    val scope = rememberCoroutineScope()

    // TODO: todo pinta a que esto se tiene que ejecutar en un onClick...
    val libraryList = scope.launch { app.db.libraryDao().getAllLibs()  }

//    if (libraryList.isNotEmpty()) {
//        LazyColumn {
//            items(libraryList.size) { index ->
//                Text(text = libraryList[index].title, modifier = Modifier.padding(top = 16.dp))
//            }
//        }
//    }

    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(text = "Regresar")
    }

}

