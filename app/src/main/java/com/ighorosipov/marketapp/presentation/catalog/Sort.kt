package com.ighorosipov.marketapp.presentation.catalog

sealed class Sort(val type: String) {
    data class PriceAscending(val value: String) : Sort(value)
    data class PriceDescending(val value: String) : Sort(value)
    data class Popularity(val value: String) : Sort(value)
}