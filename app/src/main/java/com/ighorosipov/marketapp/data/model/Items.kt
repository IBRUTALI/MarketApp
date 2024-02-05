package com.ighorosipov.marketapp.data.model

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("items") val items: List<Item>
)