package com.example.androidbooklist.data

import android.app.Application
import androidx.room.Room

class LibraryApp : Application() {

    lateinit var db: LibraryDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(this, LibraryDatabase::class.java, "library_database")
            .fallbackToDestructiveMigration()
            .build()
    }

}