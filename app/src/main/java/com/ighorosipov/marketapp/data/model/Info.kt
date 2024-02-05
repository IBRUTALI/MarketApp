package com.ighorosipov.marketapp.data.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("title") val title: String,
    @SerializedName("value") val value: String
)