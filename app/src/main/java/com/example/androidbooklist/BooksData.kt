package com.example.androidbooklist

import android.content.Context
import com.google.gson.Gson

class BooksData {

    data class Library(
        val library: List<BookItem>
    )

    data class BookItem(
        val book: Book
    )

    data class Book (
        val title: String,
        val pages: Int,
        val genre: String,
        val cover: String,
        val synopsis: String,
        val year: Int,
        val isbn: String,
        val author: Author
    )

    data class Author (
        val name: String,
        val otherBooks: List<String>
    )



    fun readBooks(context: Context, fileName: String): List<BookItem> {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val bookList = Gson().fromJson(jsonString, Library::class.java)
        return bookList.library
    }
}