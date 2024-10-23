package com.tech.ebook.data.state

import com.tech.ebook.data.response.BookCategoryModels

data class GetAllCategoryBookState(
    val isLoading : Boolean = false,
    val data : List<BookCategoryModels> = emptyList(),
    val error : Throwable ?= null
)
