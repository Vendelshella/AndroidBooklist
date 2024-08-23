package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

// TODO: crear la pantalla de detalle del libro. OJO: objeto persistente!!

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    navController: NavController,
    navigateBack: () -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    //Text(text = book.book.title)
                    Text("Titulo del libro")
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
        BodyBookDetail(navController)
    }
}

@Composable
fun BodyBookDetail(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(context = LocalContext.current)
//                .data(book.book.cover)
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            modifier = Modifier
//                .padding(start = 2.dp)
//                .fillMaxWidth()
//                .aspectRatio(1.8f)
//        )
//        Text(text = book.book.synopsis)
        Text(text = "Aqui iran la imagen y la sinopsis")
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = "Regresar")
        }

    }
}
