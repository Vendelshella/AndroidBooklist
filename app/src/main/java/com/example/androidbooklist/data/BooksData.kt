package com.example.androidbooklist.data

// TODO: Reescribir esta clase de forma más simple

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
        val ISBN: String,
        val author: Author
    )

    data class Author (
        val name: String,
        val otherBooks: List<String>
    )
}