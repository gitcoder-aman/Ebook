package com.tech.ebook

sealed class ResultState<out T> {
    data class Success<out T>(val data : T) : ResultState<T>()
    data class Error<out T>(val exception : Throwable) : ResultState<T>()
    object Loading : ResultState<Nothing>()
}