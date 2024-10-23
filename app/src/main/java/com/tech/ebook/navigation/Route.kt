package com.tech.ebook.navigation

import kotlinx.serialization.Serializable

@Serializable
data class BooksByCategoryRoute(
    val categoryName : String
)
@Serializable
object HomeRoute

@Serializable
object BookmarkScreenRoute

@Serializable
data class PdfViewUrlRoute(
    val bookUrl : String,
    val title : String,
    val author : String,
    val bookImage : String,
    val id : Int,
)