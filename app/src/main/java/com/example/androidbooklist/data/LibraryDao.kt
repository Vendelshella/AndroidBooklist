package com.example.androidbooklist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLib(library: Library)

    @Query("SELECT * FROM library")
    suspend fun getAllLibs(): List<Library>

    @Delete
    suspend fun deleteLib(library: Library)
}