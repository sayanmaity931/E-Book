package com.example.e_book.ui_layer.tabSetUp

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(ExperimentalFoundationApi::class, DelicateCoroutinesApi::class)
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun Tabs(modifier: Modifier = Modifier) {
    val list = listOf(
        "Category" to Icons.Rounded.Category,
        "All Books" to Icons.Rounded.Book,
        "Favourites" to Icons.Rounded.Fastfood
    )
    val pagerState = remember{ mutableStateOf(0) }

    TabRow(selectedTabIndex = pagerState.value,
        containerColor = Color.Black,
        indicator = {
            TabRowDefaults.Indicator(
                color = Color.White,
                height = 5.dp,
                modifier = Modifier.tabIndicatorOffset(it[pagerState.value])
            )
        }
        ) {
        list.fastForEachIndexed { index, pair ->
            Log.d("TAG","$index")
            Tab(selected = pagerState.value == index,
                selectedContentColor = Color.Gray,
                unselectedContentColor = Color.White,
                onClick = {
                    pagerState.value = index
                    Log.d("TAG","$index")
            },
                icon = {
                Icon(imageVector = pair.second , contentDescription = null
//                    tint = if (pagerState.value == index) Color.White else Color.Gray)
                )
                       },
                text = { Text(text = pair.first) }
            )
        }
    }
}