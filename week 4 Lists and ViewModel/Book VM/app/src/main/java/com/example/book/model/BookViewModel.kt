package com.example.book.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class BookViewModel: ViewModel() {
    var bookList = mutableStateListOf<Book>()

    fun add(newBook: Book){
        bookList.add(newBook)
    }

    fun delete(book: Book){
        bookList.remove(book)
    }
}