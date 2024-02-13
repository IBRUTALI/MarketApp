package com.ighorosipov.marketapp.presentation.catalog

sealed class Tag(val localString: String, val tag: String) {
    data class All(val string: String, val value: String) : Tag(string, value)
    data class Face(val string: String, val value: String) : Tag(string, value)
    data class Body(val string: String, val value: String) : Tag(string, value)
    data class Suntan(val string: String, val value: String) : Tag(string, value)
    data class Mask(val string: String, val value: String) : Tag(string, value)
}