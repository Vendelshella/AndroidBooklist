package com.example.myjsonparsing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myjsonparsing.BooksData.BookItem
import com.example.myjsonparsing.ui.theme.MyJsonParsingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // OJO: si intento sacar estas funciones del contexto, la app se cuelga
        val booksData = BooksData()
        val booksList = booksData.readBooks(context = this, "books.json")

        setContent {
            MyJsonParsingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PrintBooks(
                        library = booksList,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
    }
    }
}

@Composable
fun PrintBooks(library: List<BookItem>, modifier: Modifier) {

    //val _pagesFilter = library.map { it.book.pages }
    //var pagesFilter by remember { mutableStateOf(_pagesFilter) }

    val genreFilter = remember { library.map { it.book.genre }.distinct() }
    var selectedGenre by remember { mutableStateOf(genreFilter.first()) }


    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "${library.size} libros disponibles",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 28.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Filtrar por páginas",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center
                    )/*
                    BookFilter(
                        current = selectedGenre,
                        filters = pagesFilter, // Esto es <Int> y tiene que ser <String>
                        onFilterClicked = {
                            pagesFilter = it
                        }
                    )*/
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                ){
                    Text(
                        text = "Filtrar por género",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                    )
                    BookFilter(
                        current = selectedGenre,
                        filters = genreFilter,
                        onFilterClicked = {
                            selectedGenre = it
                        })
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 14.dp)
            ) {
                items(library.size) { index ->
                    BookCard(bookRes = library[index])
                }
            }
        }
    }
}

@Composable
fun BookCard(bookRes: BookItem) {
    Card (
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(bookRes.book.cover)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth()
                .aspectRatio(1.0f)
        )
        Text(
            text = bookRes.book.title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth(),
            fontSize = 12.sp
        )
    }
}

// TODO: implementar dentro del 'onClick' la lógica de volver a mostrar la lista de libros
@Composable
fun BookFilter(
    current: String,
    filters: List<String>,
    onFilterClicked: (String) -> Unit
) {
    var showDropdown by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable { showDropdown = true }
            .padding(8.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {showDropdown = true}
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Filter by genre"
                )
            }
            Text(text = current)
        }


        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            filters.forEach { eachFilter ->
                DropdownMenuItem(
                    text = { Text(eachFilter) },
                    onClick = {
                        onFilterClicked(eachFilter)
                        showDropdown = false
                    }
                )
            }
        }
    }
}
