package com.tech.ebook.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tech.ebook.local.viewmodel.RoomBookmarkViewModel
import com.tech.ebook.presentation.screens.BookByCategory
import com.tech.ebook.presentation.screens.BookmarkScreen
import com.tech.ebook.presentation.screens.PdfViewBook
import com.tech.ebook.ui_layer.tabSetUp.TabLayout
import com.tech.ebook.viewmodel.BookViewmodel

@Composable
fun NavApp() {

    val navController = rememberNavController()
    val bookViewmodel : BookViewmodel = hiltViewModel()
    val roomBookmarkViewModel : RoomBookmarkViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = HomeRoute){

        composable<HomeRoute> {
            TabLayout(navController)
        }
        composable<BooksByCategoryRoute> {
            val categoryName = it.toRoute<BooksByCategoryRoute>().categoryName
            BookByCategory(categoryName,bookViewmodel,navController)
        }
        composable<BookmarkScreenRoute> {
            BookmarkScreen(roomBookmarkViewModel,navController)
        }
        composable<PdfViewUrlRoute> {
            val bookUrl = it.toRoute<PdfViewUrlRoute>().bookUrl
            val bookImage = it.toRoute<PdfViewUrlRoute>().bookImage
            val title = it.toRoute<PdfViewUrlRoute>().title
            val author = it.toRoute<PdfViewUrlRoute>().author
            val id = it.toRoute<PdfViewUrlRoute>().id
            PdfViewBook(bookUrl,bookImage,title,author,id,navController, roomBookmarkViewModel)
        }
    }
    
}