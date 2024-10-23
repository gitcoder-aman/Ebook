package com.tech.ebook.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookModels(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val bookName : String = "",
    val bookImage : String = "",
    val bookUrl : String = "",
    val category : String = "",
    val bookAuthor : String = "",
)
