package com.tech.ebook.ui_layer.tabSetUp

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tech.ebook.navigation.BookmarkScreenRoute
import com.tech.ebook.navigation.HomeRoute
import com.tech.ebook.presentation.screens.BookScreen
import com.tech.ebook.presentation.screens.CategoryScreen
import com.tech.ebook.viewmodel.BookViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabLayout(navController: NavHostController) {

    val bookViewmodel: BookViewmodel = hiltViewModel()
    val list = listOf(
        TabItem(
            title = "Category",
            selectedIcon = Icons.Filled.Category,
            unselectedIcon = Icons.Outlined.Category
        ),
        TabItem(
            title = "All Books",
            selectedIcon = Icons.Filled.Book,
            unselectedIcon = Icons.Outlined.Book
        )
    )
    val pagerState = rememberPagerState(pageCount = { list.size })
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Green,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Book App")
            }, actions = {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.padding(4.dp).clickable {
                        navController.navigate(BookmarkScreenRoute)
                    }
                )
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Green
            ) {
                list.forEachIndexed { index, item ->
                    Tab(selected = pagerState.currentPage == index, onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, icon = {
                        Icon(
                            imageVector = if (pagerState.currentPage == index) {
                                item.selectedIcon
                            } else
                                item.unselectedIcon, contentDescription = item.title
                        )
                    }, text = {
                        Text(text = item.title)
                    })
                }
            }
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> CategoryScreen(bookViewmodel, navController)
                    1 -> BookScreen(bookViewmodel, navController)
                }
            }
        }
    }


}

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)