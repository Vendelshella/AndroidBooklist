package com.example.androidbooklist.data

import android.content.Context
import com.example.androidbooklist.R
import com.example.androidbooklist.data.BooksData.BookItem
import com.example.androidbooklist.data.BooksData.Library
import com.google.gson.Gson

class BooksDataSource {

    fun readBooks(context: Context): List<BookItem> {
        val fileName = context.getString(R.string.data_source)
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val bookList = Gson().fromJson(jsonString, Library::class.java)
        return bookList.library
    }

    fun getBook(context: Context, isbn: String): List<BookItem> {
        val library = readBooks(context)
        var book = library.filter { it.book.isbn == isbn }
        return book
    }
}