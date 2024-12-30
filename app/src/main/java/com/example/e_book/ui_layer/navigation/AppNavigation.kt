package com.example.e_book.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.ui_layer.navigation.routes.BooksByCategoryScreen
import com.example.e_book.ui_layer.navigation.routes.HomeScreen
import com.example.e_book.ui_layer.navigation.routes.PdfViewScreen
import com.example.e_book.ui_layer.screen.AllBooksScreen
import com.example.e_book.ui_layer.screen.BookByCategory
import com.example.e_book.ui_layer.screen.PdfViewScreenUi
import com.example.e_book.ui_layer.tabSetUp.Tabs1

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = routes.HomeScreen){

        composable<HomeScreen>{
            Tabs1(modifier = modifier , navController = navController)
        }

       composable<PdfViewScreen>{
           val data = it.toRoute<PdfViewScreen>()
           PdfViewScreenUi(pdfUrl = data.pdfUrl)
       }

        composable<BooksByCategoryScreen>{
            val data = it.toRoute<BooksByCategoryScreen>()
            BookByCategory(navController = navController , category = data.category)
        }


    }
}