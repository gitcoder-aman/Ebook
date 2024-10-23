package com.tech.ebook.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tech.ebook.R
import com.tech.ebook.navigation.PdfViewUrlRoute
import com.tech.ebook.viewmodel.BookViewmodel

@Composable
fun BookScreen(bookViewModel: BookViewmodel, navController: NavHostController) {

    val allBooksState = bookViewModel.getAllBooksState.collectAsState()
    val bookList = allBooksState.value.data

    when {
        allBooksState.value.isLoading -> {
            Log.d("@bookScreen", "BookScreen: Loading...")
        }

        allBooksState.value.data.isNotEmpty() -> {
            Log.d("@bookScreen", "BookScreen: ${allBooksState.value.data}")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(bookList) {
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

        allBooksState.value.error != null -> {
            Log.d("@bookScreen", "BookScreen: ${allBooksState.value.error}")
        }
    }

}

@Preview
@Composable
fun Books(
    title: String = "Android",
    bookImage: String = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
    bookAuthor: String = "Author",
    onClick: () -> Unit = {}
) {

    Log.d("@book", "Books: $bookImage")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.Gray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = bookImage,
                contentDescription = title,
                modifier = Modifier.size(100.dp),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(Modifier.height(8.dp))
                Text(text = bookAuthor)
            }
        }
    }
}
