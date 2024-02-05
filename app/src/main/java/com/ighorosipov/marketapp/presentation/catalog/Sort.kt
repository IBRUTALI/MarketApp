package com.ighorosipov.marketapp.presentation.catalog

sealed class Sort {
    data object Ascending: Sort()
    data object Descending: Sort()
    data object Popularity: Sort()
}