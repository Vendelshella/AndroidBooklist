package com.example.androidbooklist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "library")
data class Library(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val ISBN: String,
    val cover: String
)
