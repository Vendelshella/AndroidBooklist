package com.example.myjsonparsing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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

    LazyColumn {
        item{
            Text(
                text = "TOTAL ELEMENTOS ${library.size}",
                modifier = Modifier.padding(top = 28.dp)
            )
        }
        items(library) { libro ->
            Text(
                text = libro.book.title,
                modifier = Modifier.padding(top = 4.dp)
            )
            // Consigo recuperar las URL:
            println("URL: ${libro.book.cover}")

            // TODO: no logro pintar la imagen... (aunque la app no se cuelga):
            Image(
                painter = rememberAsyncImagePainter(libro.book.cover),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
