package com.example.androidbooklist.data.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "read_books")
data class ReadBook(
    @PrimaryKey val isbn: String
)

@Dao
interface BooksDao {
    @Insert
    suspend fun persist(readBook: ReadBook)

    @Query("SELECT * FROM read_books WHERE isbn = :isbn")
    suspend fun get(isbn: String): ReadBook?

    @Query("SELECT * FROM read_books")
    suspend fun getAll(): List<ReadBook>

    @Delete
    suspend fun remove(readBook: ReadBook)

    @Query("DELETE FROM read_books WHERE isbn = :isbn")
    suspend fun remove(isbn: String)
}

@Database(entities = [ReadBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}