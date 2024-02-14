package com.ighorosipov.marketapp.presentation.catalog

import androidx.annotation.StringRes
import com.ighorosipov.marketapp.R

sealed class Sort(@StringRes val resId: Int) {
    data class PriceAscending(@StringRes val id: Int = R.string.by_price_ascend) : Sort(id)
    data class PriceDescending(@StringRes val id: Int = R.string.by_price_descend) : Sort(id)
    data class Popularity(@StringRes val id: Int = R.string.by_popularity) : Sort(id)
}