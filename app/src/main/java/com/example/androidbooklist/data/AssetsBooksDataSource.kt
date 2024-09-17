package com.example.androidbooklist.data

import android.content.Context
import com.example.androidbooklist.R
import com.example.androidbooklist.data.BooksData.BookItem
import com.example.androidbooklist.data.BooksData.Library
import com.google.gson.Gson

class AssetsBooksDataSource(private val context: Context) {

    /**
     * Lee el DataSource y devuelve una lista con el contenido del JSON
     * */
    fun readBooks(): List<BookItem> {
        val fileName = context.getString(R.string.data_source)
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val bookList = Gson().fromJson(jsonString, Library::class.java)
        return bookList.library
    }

    /**
     * Devuelve un libro en función del ISBN; como '.filter{}' por defecto siempre devuelve una lista,
     * yo devuelvo el elemento [0] de la lista, ya que sé que el ISBN es un id único.
     * */
    fun getBook(isbn: String): BookItem {
        val library = readBooks()
        val book = library.filter { it.book.ISBN == isbn }
        return book[0]
    }
}