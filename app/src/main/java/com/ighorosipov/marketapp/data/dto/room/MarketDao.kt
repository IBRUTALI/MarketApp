package com.ighorosipov.marketapp.data.dto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ighorosipov.marketapp.data.model.db.FavoriteEntity
import com.ighorosipov.marketapp.data.model.db.UserEntity

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("SELECT * FROM favorite WHERE item =:itemId LIMIT 1")
    suspend fun findFavoriteById(itemId: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE item =:itemId")
    suspend fun deleteUserFavorite(itemId: String)

    @Query("SELECT item FROM favorite")
    suspend fun getUserFavorites(): List<String>

}