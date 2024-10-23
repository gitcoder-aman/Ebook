package com.tech.ebook.presentation.screens

import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.tech.ebook.data.response.BookModels
import com.tech.ebook.local.viewmodel.RoomBookmarkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewBook(
    bookUrl: String,
    bookImage: String,
    title: String,
    author: String,
    id: Int,
    navController: NavHostController,
    roomBookmarkViewModel: RoomBookmarkViewModel
) {

//    val pdfState = rememberVerticalPdfReaderState(
//        resource = ResourceType.Remote(pdfUrl),
//        isZoomEnable = true
//    )
//    VerticalPDFReader(
//        state = pdfState,
//        modifier = Modifier.fillMaxSize().background(
//            Color.Gray
//        )
//    )
    val context = LocalContext.current
    val bookmarked = roomBookmarkViewModel.bookmarked.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(title)
                        Text(author)
                    }
                }, actions = {
                    Icon(
                        imageVector = if (bookmarked.value) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "bookmark",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                roomBookmarkViewModel.bookmarkBook(
                                    BookModels(
                                        bookName = title,
                                        bookAuthor = author,
                                        bookUrl = bookUrl,
                                        bookImage = bookImage
                                    )
                                )
                                Toast
                                    .makeText(context, "Save Bookmark", Toast.LENGTH_SHORT)
                                    .show()

                            }
                    )
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
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    // Enable JavaScript if needed
                    settings.javaScriptEnabled = true
                    // Load the book URL
                    loadUrl(bookUrl)
                }
            },
            update = { webView ->
                // Update the webView if necessary
                webView.loadUrl(bookUrl)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // Fill the available space
        )
    }

}