package com.example.e_book.ui_layer.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.ui_layer.navigation.routes
import com.example.e_book.ui_layer.viewModel.AppViewModel

@Composable
fun BookByCategory(viewModel: AppViewModel = hiltViewModel(), navController: NavController , category : String) {

    val state = viewModel.getBooksByCategoryState.collectAsState()

    val data = state.value.data

    LaunchedEffect(key1 = Unit){
        viewModel.getBooksByCategory(category)
    }

    when {
        state.value.isLoading -> {
            CircularProgressIndicator()
        }
        state.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
                Text(text = state.value.error.toString())
            }
        }

        state.value.data.isNotEmpty() -> {
            LazyColumn {
                items(data){
                   books(
                    title = it.bookName,
                    url = it.bookUrl,
                    bookImage = it.bookImage,
                    author = it.bookAuthor,
                       onItemClick = {
                         navController.navigate(routes.PdfViewScreen(it.bookUrl))
                     }
                   )
                }
            }
        }
    }
}