package com.example.e_book.ui_layer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.ResultState
import com.example.e_book.repo.Repo
import com.example.e_book.response.BookCategoryModels
import com.example.e_book.response.BookModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo : Repo) : ViewModel() {

    private val _getAllBooksState = MutableStateFlow(GetAllBooksState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    private val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    private val _getBooksByCategoryState = MutableStateFlow(GetBooksByCategoryState())
    val getBooksByCategoryState = _getBooksByCategoryState.asStateFlow()

    fun getAllBooks() {
        viewModelScope.launch {
            repo.getAllBooks().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllBooksState.value = GetAllBooksState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllBooksState.value =
                            GetAllBooksState(data = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllBooksState.value =
                            GetAllBooksState(error = it.exception, isLoading = false)
                    }
                }
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            repo.getAllCategories().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllCategoryState.value = GetAllCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllCategoryState.value =
                            GetAllCategoryState(data = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllCategoryState.value =
                            GetAllCategoryState(error = it.exception, isLoading = false)
                    }
                }
            }

        }
    }

    fun getBooksByCategory(category : String){
        viewModelScope.launch {
            repo.getBooksByCategory(category).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getBooksByCategoryState.value = GetBooksByCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getBooksByCategoryState.value =
                            GetBooksByCategoryState(data = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getBooksByCategoryState.value =
                            GetBooksByCategoryState(error = it.exception, isLoading = false)
                    }
                }
            }
        }

    }
}


     data class GetAllBooksState(
        val data: List<BookModels> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )

    data class GetAllCategoryState(
        val data: List<BookCategoryModels> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )

    data class GetBooksByCategoryState(
        val data: List<BookModels> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )
