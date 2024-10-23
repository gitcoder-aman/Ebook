package com.tech.ebook.presentation.repo

import com.tech.ebook.ResultState
import com.tech.ebook.data.response.BookCategoryModels
import com.tech.ebook.data.response.BookModels
import kotlinx.coroutines.flow.Flow

interface BookRepository  {
    suspend fun getAllBooks() : Flow<ResultState<List<BookModels>>>
    suspend fun getAllCategoryBook() : Flow<ResultState<List<BookCategoryModels>>>
    suspend fun getBookByCategory(category : String) : Flow<ResultState<List<BookModels>>>
}