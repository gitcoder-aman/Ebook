package com.tech.ebook.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tech.ebook.navigation.PdfViewUrlRoute
import com.tech.ebook.viewmodel.BookViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookByCategory(
    categoryName: String,
    bookViewmodel: BookViewmodel,
    navController: NavHostController
) {

    Log.d("@cat", "BookByCategory: ${categoryName}")

    LaunchedEffect(Unit) {
        bookViewmodel.getBookByCategory(categoryName)
    }
    val getBookByCategoryState = bookViewmodel.getBookByCategoryState.collectAsState()
    val categoryBookList = getBookByCategoryState.value.data
    val context = LocalContext.current

    when {
        getBookByCategoryState.value.isLoading -> {
            CircularProgressIndicator()
        }

        getBookByCategoryState.value.data.isNotEmpty() -> {

            Log.d("@cat", "BookByCategory: ${getBookByCategoryState.value.data.size}")

            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Green,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Book App")
                    }, navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    navController.navigateUp()
                                }
                        )
                    }
                )
            }) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(categoryBookList) {
                        Books(
                            title = it.bookName,
                            bookImage = it.bookImage,
                            bookAuthor = it.bookAuthor
                        ) {
                            navController.navigate(
                                PdfViewUrlRoute(
                                    bookUrl = it.bookUrl,
                                    bookImage = it.bookImage,
                                    title = it.bookName,
                                    author = it.bookAuthor,
                                    id = it.id
                                )
                            )
                        }
                    }
                }
            }
        }

        getBookByCategoryState.value.error != null -> {
            Toast.makeText(
                context,
                getBookByCategoryState.value.error.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}