package com.ighorosipov.marketapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "items")
data class Item(
    @SerializedName("available")
    @ColumnInfo(name = "available")
    val available: Int,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @SerializedName("feedback")
    @ColumnInfo(name = "feedback")
    val feedback: Feedback,

    @SerializedName("id")
    @PrimaryKey
    val id: String,

    @SerializedName("info")
    @ColumnInfo(name = "info")
    val info: List<Info>,

    @SerializedName("ingredients")
    @ColumnInfo(name = "ingredients")
    val ingredients: String,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Price,

    @SerializedName("subtitle")
    @ColumnInfo(name = "subtitle")
    val subtitle: String,

    @SerializedName("tags")
    @ColumnInfo(name = "tags")
    val tags: List<String>,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,

)