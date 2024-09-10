package com.example.androidbooklist.data

import android.app.Application
import androidx.room.Room
import kotlin.properties.Delegates

class LibraryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(this, LibraryDatabase::class.java, "library_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var db: LibraryDatabase by Delegates.notNull()
            private set
    }

}