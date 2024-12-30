package com.example.e_book.ui_layer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class routes{

    @Serializable
    data class BooksByCategoryScreen(val category: String)

    @Serializable
    data class PdfViewScreen(val pdfUrl: String)

    @Serializable
    object HomeScreen
}