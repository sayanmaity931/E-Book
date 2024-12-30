package com.example.e_book.ui_layer.tabSetUp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.e_book.ui_layer.screen.AllBooksScreen
import com.example.e_book.ui_layer.screen.CategoryScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs1(modifier: Modifier = Modifier , navController: NavController) {

    val tabs = listOf(
        "Category" to Icons.Rounded.Category,
        "All Books" to Icons.Rounded.Book
    )

    val pagerState1 = rememberPagerState(pageCount = {tabs.size})

    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState1.currentPage,
            modifier = modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, pair ->

                Tab(selected = pagerState1.currentPage == index,
                    modifier = modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            pagerState1.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = pair.first) },
                    icon = { Icon(imageVector = pair.second, contentDescription = null) }
                )
            }
        }
        HorizontalPager(state = pagerState1){
            when(it){

                0 -> CategoryScreen(navController = navController)
                1 -> AllBooksScreen(navController = navController)

            }
        }
    }
}

