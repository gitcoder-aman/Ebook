package com.tech.ebook.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tech.ebook.data.response.BookModels
import kotlinx.coroutines.flow.Flow

@Dao
interface EbookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book : BookModels)

    @Query("SELECT * FROM book_table")
    fun getAllBookList(): Flow<List<BookModels>>

    @Query("DELETE FROM book_table WHERE id = :bookId")
    suspend fun deleteBook(bookId: Int)

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): BookModels?

}