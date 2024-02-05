package com.ighorosipov.marketapp.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "number") val number: String,
)
