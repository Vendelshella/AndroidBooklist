package com.example.androidbooklist

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidbooklist.data.room.AppDatabase
import kotlin.properties.Delegates

class BooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "books.db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    companion object {
        var database by Delegates.notNull<AppDatabase>()
            private set
    }
}