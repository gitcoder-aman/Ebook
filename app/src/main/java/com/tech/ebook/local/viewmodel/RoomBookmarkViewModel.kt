package com.tech.ebook.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.ebook.data.response.BookModels
import com.tech.ebook.local.dao.EbookDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomBookmarkViewModel @Inject constructor(
    private val bookDao: EbookDao
) : ViewModel() {

    private val _getBookList = MutableStateFlow<List<BookModels>>(emptyList())
    val getBookList = _getBookList.asStateFlow()

    private val _bookmarked = MutableStateFlow(false)
    val bookmarked = _bookmarked.asStateFlow()

    init {
        getAllBookmarkList()
    }

    fun bookmarkBook(book: BookModels) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }

    fun deleteBookById(id: Int) {
        viewModelScope.launch {
            bookDao.deleteBook(id)
        }
    }

    fun getAllBookmarkList() {
        viewModelScope.launch {
            bookDao.getAllBookList().collect {
                _getBookList.value = it
            }
        }
    }
    fun checkBookmarked(id: Int) {
        viewModelScope.launch {
            if(bookDao.getBookById(id) != null){
                _bookmarked.value = true
            }else{
                _bookmarked.value = false
            }
        }
    }
}