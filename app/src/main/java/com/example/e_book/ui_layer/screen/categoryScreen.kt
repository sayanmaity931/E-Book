package com.example.e_book.ui_layer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_book.ui_layer.navigation.routes
import com.example.e_book.ui_layer.viewModel.AppViewModel


@Composable
    fun CategoryScreen(viewModel: AppViewModel = hiltViewModel() , navController: NavController){

        val state = viewModel.getAllCategoryState.collectAsState()

        LaunchedEffect(key1 = Unit){
            viewModel.getAllCategory()
        }

        when{
            state.value.isLoading -> {
                CircularProgressIndicator()
            }
            state.value.error != null -> {
                Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
                    Text(text = state.value.error.toString())
                }
            }
            state.value.data.isNotEmpty() -> {
                Column (modifier = Modifier.fillMaxSize()){

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.value.data) {
                        Card (
                            modifier = Modifier.clickable{
                                navController.navigate(routes.BooksByCategoryScreen(it.name))
                            }
                        ){
                            Categories(
                                name = it.name,
                                categoryImage = it.categoryImageUrl
                            )
                        }

                        }

                    }
                }
            }
        }

    }

@Composable
fun Categories(
    name : String = "",
    categoryImage : String = ""
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = categoryImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(5.dp)
            )
            Text(
                text = name,
                color = androidx.compose.ui.graphics.Color.White ,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)

            )
        }
    }
}
