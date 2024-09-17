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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooklist.R
import com.example.androidbooklist.data.AssetsBooksDataSource
import com.example.androidbooklist.data.BooksRepository
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
    val repository = remember { BooksRepository.create(context.applicationContext) }

    val coroutineScope = rememberCoroutineScope()
    val database = remember { LibraryApp.db.libraryDao() }

    val thisBook = remember { repository.getBook(isbn = isbn) }
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
                    Text(text = stringResource(R.string.title_book_detail))
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
                text = thisBook.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = thisBook.synopsis,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.padding(top = 48.dp))
            Row{
                Column(modifier = Modifier.weight(1f)){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(thisBook.cover)
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
                        Text(text = stringResource(
                            R.string.text_detail_author,
                            thisBook.author
                        ), style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = stringResource(
                            R.string.text_detail_year,
                            thisBook.year
                        ), style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = stringResource(
                            R.string.text_detail_pages,
                            thisBook.pages
                        ), style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = stringResource(
                            R.string.text_detail_isbn,
                            thisBook.ISBN
                        ), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))
            Row{
                Button(
                    onClick = { navController.popBackStack() },
                    shape = RectangleShape
                ) {
                    Text(text = stringResource(R.string.text_button_back))
                }
                Spacer(modifier = Modifier.padding(start = 32.dp))


                Button(
                    onClick = { coroutineScope.launch {
                        val candidate = database.getLibByIsbn(isbn = isbn)
                        isAdded = if (candidate == null) {
//                            repository.add(thisBook)
                            database.insertLib(
                                Library(
                                title = thisBook.title,
                                author = thisBook.author,
                                ISBN = thisBook.ISBN,
                                cover = thisBook.cover
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
                    Text(text = if (!isAdded) stringResource(
                        R.string.text_button_add) else stringResource(
                        R.string.text_button_remove)
                    )
                }
            }
        }
    }
}