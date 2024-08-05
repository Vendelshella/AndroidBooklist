package com.example.myjsonparsing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    }
}
