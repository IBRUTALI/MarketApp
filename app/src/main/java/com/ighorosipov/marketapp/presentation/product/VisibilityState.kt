package com.ighorosipov.marketapp.presentation.product

sealed class VisibilityState {
    data object VISIBLE : VisibilityState()
    data object GONE : VisibilityState()
}