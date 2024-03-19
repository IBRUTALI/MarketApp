package com.ighorosipov.marketapp.domain.repository

import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.utils.Result

interface MarketRepository {

    suspend fun getItemsCached(): Result<List<Item>>

    suspend fun getItems(): Result<List<Item>>

    suspend fun getItemById(itemId: String): Item

    suspend fun updateItem(item: Item)

    suspend fun insertItem(item: Item)

    suspend fun insertUser(user: User)

    suspend fun getUser(): User?

    suspend fun deleteUser(user: User)

    suspend fun isUserSignIn(): Boolean

    suspend fun getFavoritesCount(): Int

    suspend fun getUserFavorites(): List<Item>
}