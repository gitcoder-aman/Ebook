package com.tech.ebook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.ebook.ResultState
import com.tech.ebook.data.state.GetAllBookState
import com.tech.ebook.data.state.GetAllCategoryBookState
import com.tech.ebook.data.state.GetBookByCategoryState
import com.tech.ebook.presentation.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewmodel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _getAllBooksState = MutableStateFlow(GetAllBookState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    private val _getAllCategoryBooksState = MutableStateFlow(GetAllCategoryBookState())
    val getAllCategoryBooksState = _getAllCategoryBooksState.asStateFlow()

    private val _getBookByCategoryState = MutableStateFlow(GetBookByCategoryState())
    val getBookByCategoryState = _getBookByCategoryState.asStateFlow()

    init {
        getAllBooks()
        getAllCategoryBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooks().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllBooksState.value = GetAllBookState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllBooksState.value = GetAllBookState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllBooksState.value = GetAllBookState(error = it.exception)
                    }
                }
            }
        }
    }

    private fun getAllCategoryBooks() {
        viewModelScope.launch {
            bookRepository.getAllCategoryBook().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllCategoryBooksState.value = GetAllCategoryBookState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllCategoryBooksState.value = GetAllCategoryBookState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllCategoryBooksState.value =
                            GetAllCategoryBookState(error = it.exception)
                    }
                }
            }
        }
    }

    fun getBookByCategory(category: String) {
        viewModelScope.launch {
            bookRepository.getBookByCategory(category).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getBookByCategoryState.value = GetBookByCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getBookByCategoryState.value = GetBookByCategoryState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _getBookByCategoryState.value = GetBookByCategoryState(error = it.exception)
                    }
                }
            }

        }
    }
}
