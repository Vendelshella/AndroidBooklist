package com.example.androidbooklist.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooklist.data.BooksData
import com.example.androidbooklist.data.BooksDataSource
import com.example.androidbooklist.data.Library
import com.example.androidbooklist.data.LibraryApp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    isbn: String,
    navController: NavController,
    navigateBack: () -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val database = remember { LibraryApp.db.libraryDao() }

    val thisBook = remember {
        BooksDataSource.getBook(context = context, isbn = isbn)
    }

    var isAdded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAdded = database.getLibByIsbn(isbn = isbn) != null
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Detalle del libro")
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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = thisBook.book.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = thisBook.book.synopsis,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.padding(top = 48.dp))
            Row{
                Column(modifier = Modifier.weight(1f)){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(thisBook.book.cover)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 2.dp, top = 2.dp)
                            .fillMaxWidth()
                            .aspectRatio(0.8f)
                    )
                }
                Column(modifier = Modifier.weight(1f)){
                    Column{
                        Text(text = "- Autor: ${thisBook.book.author.name}", style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = "- Año de publicación: ${thisBook.book.year}", style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = "- ${thisBook.book.pages} páginas", style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = "- ISBN: ${thisBook.book.ISBN}", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))
            Row{
                Button(
                    onClick = { navController.popBackStack() },
                    shape = RectangleShape
                ) {
                    Text(text = "Volver")
                }
                Spacer(modifier = Modifier.padding(start = 32.dp))


                Button(
                    onClick = { coroutineScope.launch {
                        val candidate = database.getLibByIsbn(isbn = isbn)
                        isAdded = if (candidate == null) {
                            database.insertLib(
                                Library(
                                title = thisBook.book.title,
                                author = thisBook.book.author.name,
                                ISBN = thisBook.book.ISBN,
                                cover = thisBook.book.cover
                                )
                            )
                            true
                        } else {
                            database.deleteLib(candidate)
                            false
                        }
                    } },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = if (!isAdded) "Añadir a biblioteca" else "Eliminar de biblioteca")
                }
            }
        }
    }
}