package com.ighorosipov.marketapp.domain.model

data class Price(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
)