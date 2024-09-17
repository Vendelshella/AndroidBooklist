package com.example.androidbooklist.data

import android.content.Context

class BooksRepository (
    private val assetsBooksDataSource : AssetsBooksDataSource,
    private val sqlBooksDataSource : LibraryDao
) {
    fun getAll(): List<Book> {
        return assetsBooksDataSource.readBooks()
            .map { bookItem -> bookItem.toBook() }
    }

    fun getBook(isbn: String): Book {
        return assetsBooksDataSource.getBook(isbn).toBook()
    }

    suspend fun getAllRead(): List<Book> {
        return getAll().filter { isRead(it.ISBN) }
    }

    suspend fun isRead(isbn: String): Boolean {
        return sqlBooksDataSource.getLibByIsbn(isbn) != null
    }

    suspend fun add(book: Book) {
        sqlBooksDataSource.insertLib(
            Library(
                title = book.title,
                author = "",
                ISBN = book.ISBN,
                cover = book.cover
            )
        )
    }

    suspend fun delete(book: Book) {
        sqlBooksDataSource.deleteLib(book.ISBN)
    }

    // Factoría para crear repository y no meter esto en todas las pantallas.
    companion object {
        fun create(context: Context): BooksRepository{
            return BooksRepository(
                AssetsBooksDataSource(context),
                LibraryApp.db.libraryDao()
            )
        }
    }
}

data class Book (
    val title: String,
    val pages: Int,
    val genre: String,
    val cover: String,
    val ISBN: String,
    val author: String,
    val synopsis: String,
    val year: Int
)

// Función de extensión que convierte un BookItem en un Book.
// Es como estar dentro de la clase BookItem y acceder a sus propiedades.
fun BooksData.BookItem.toBook(): Book {
    return Book(
        title = this.book.title,
        pages = this.book.pages,
        genre = this.book.genre,
        cover = this.book.cover,
        ISBN =this.book.ISBN,
        author = this.book.author.name,
        synopsis = this.book.synopsis,
        year = this.book.year
    )
}
