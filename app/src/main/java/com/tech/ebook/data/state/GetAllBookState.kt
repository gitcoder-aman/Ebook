package com.tech.ebook.data.state

import com.tech.ebook.data.response.BookModels

data class GetAllBookState(
    val isLoading : Boolean = false,
    val data : List<BookModels> = emptyList(),
    val error : Throwable ?= null
)
