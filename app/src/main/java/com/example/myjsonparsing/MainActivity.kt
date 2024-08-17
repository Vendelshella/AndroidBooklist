package com.example.myjsonparsing


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myjsonparsing.navigation.Navigation
import com.example.myjsonparsing.ui.theme.MyJsonParsingTheme
import com.example.myjsonparsing.utils.filterBooks


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val booksData = BooksData()
//        val booksList = booksData.readBooks(context = this, "books.json")

        setContent {
            MyJsonParsingTheme {

//                val pagesFilter = remember { booksList.map { it.book.pages }.sorted() }
//                var selectedPageNumber by remember { mutableIntStateOf(pagesFilter.last()) }
//
//                val genreFilter = remember { booksList.map { it.book.genre }.distinct() }
//                var selectedGenre by remember { mutableStateOf("") }
//
//                val filteredBooks = filterBooks(
//                    books = booksList,
//                    genre = selectedGenre,
//                    page = selectedPageNumber
//                )

                Navigation()

            }
        }
    }
}
