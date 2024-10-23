package com.tech.ebook.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tech.ebook.navigation.BooksByCategoryRoute
import com.tech.ebook.presentation.screens.component.CategoryItemView
import com.tech.ebook.viewmodel.BookViewmodel

@Composable
fun CategoryScreen(bookViewmodel: BookViewmodel, navController: NavHostController) {
    val allCategoryBooksState = bookViewmodel.getAllCategoryBooksState.collectAsState()
    val categoryBookList = allCategoryBooksState.value.data

    when {
        allCategoryBooksState.value.isLoading -> {
            Log.d("@bookScreen", "BookScreen: Loading...")
        }

        allCategoryBooksState.value.data.isNotEmpty() -> {
            Log.d("@bookScreen", "BookScreen: ${allCategoryBooksState.value.data}")

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Number of columns
                modifier = Modifier,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(categoryBookList) {
                    CategoryItemView(it.name, it.categoryImageUrl) {
                        navController.navigate(BooksByCategoryRoute(it.name))
                    }
                }
            }
        }

        allCategoryBooksState.value.error != null -> {
            Log.d("@bookScreen", "BookScreen: ${allCategoryBooksState.value.error}")
        }
    }
}