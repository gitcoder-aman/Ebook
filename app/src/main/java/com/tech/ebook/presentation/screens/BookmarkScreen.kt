package com.tech.ebook.presentation.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tech.ebook.R
import com.tech.ebook.local.viewmodel.RoomBookmarkViewModel
import com.tech.ebook.navigation.PdfViewUrlRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(roomBookmarkViewModel: RoomBookmarkViewModel, navController: NavHostController) {

    LaunchedEffect(
        Unit
    ) {
        roomBookmarkViewModel.getAllBookmarkList()
    }
    val context = LocalContext.current
    val getAllBookMarkList = roomBookmarkViewModel.getBookList.collectAsState()
    Log.d("@room", "PdfViewBook: ${getAllBookMarkList.value.size}")
    val bookList = getAllBookMarkList.value ?: emptyList()

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Bookmarks")
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
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(bookList) {
                BooksOfBookmark(
                    title = it.bookName,
                    bookImage = it.bookImage,
                    bookAuthor = it.bookAuthor,
                    onClickBook = {
                        navController.navigate(
                            PdfViewUrlRoute(
                                bookUrl = it.bookUrl,
                                bookImage = it.bookImage,
                                title = it.bookName,
                                author = it.bookAuthor,
                                id = it.id
                            )
                        )
                    },
                    onClickDelete = {
                        roomBookmarkViewModel.deleteBookById(it.id)
                        Toast
                            .makeText(context, "Remove Bookmark", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun BooksOfBookmark(
    title: String = "Android",
    bookImage: String = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
    bookAuthor: String = "Author",
    onClickBook: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {

    Log.d("@book", "Books: $bookImage")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClickBook() }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(4.dp)
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
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClickDelete()
                    }
            )
        }
    }
}