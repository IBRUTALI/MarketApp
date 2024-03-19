package com.ighorosipov.marketapp.data.dto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ighorosipov.marketapp.data.model.db.ItemEntity
import com.ighorosipov.marketapp.data.model.db.UserEntity

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ItemEntity

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("SELECT * FROM items")
    suspend fun getItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("SELECT * FROM items WHERE isFavorite = 1")
    suspend fun getUserFavorites(): List<ItemEntity>

    @Query("SELECT id FROM items WHERE isFavorite = 1")
    suspend fun getUserFavoritesIds(): List<String>

    @Query("SELECT COUNT(id) FROM items WHERE isFavorite = 1")
    suspend fun getUserFavoritesCount(): Int

}