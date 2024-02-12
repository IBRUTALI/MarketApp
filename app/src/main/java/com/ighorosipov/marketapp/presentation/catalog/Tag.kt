package com.ighorosipov.marketapp.presentation.catalog

sealed class Tag(tag: String) {
    data class All(val tag: String) : Sort(tag)
    data class Face(val tag: String) : Sort(tag)
    data class Suntan(val tag: String) : Sort(tag)
    data class Mask(val tag: String) : Sort(tag)
}