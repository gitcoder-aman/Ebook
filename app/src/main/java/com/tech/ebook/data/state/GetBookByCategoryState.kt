package com.tech.ebook.data.state

import com.tech.ebook.data.response.BookModels

data class GetBookByCategoryState(
    val isLoading : Boolean = false,
    val data : List<BookModels> = emptyList(),
    val error : Throwable ?= null
)
