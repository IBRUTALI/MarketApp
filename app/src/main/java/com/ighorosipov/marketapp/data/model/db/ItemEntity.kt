package com.ighorosipov.marketapp.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ighorosipov.marketapp.data.model.Feedback
import com.ighorosipov.marketapp.data.model.Info
import com.ighorosipov.marketapp.data.model.Price

@Entity(tableName = "items")
data class ItemEntity(
    @ColumnInfo(name = "available") val available: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "feedback") val feedback: Feedback,
    @PrimaryKey val id: String,
    @ColumnInfo(name = "info") val info: List<Info>,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "price") val price: Price,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "tags") val tags: List<String>,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)