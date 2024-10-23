package com.tech.ebook.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.ebook.data.response.BookModels
import com.tech.ebook.local.dao.EbookDao

@Database(entities = [BookModels::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): EbookDao
}