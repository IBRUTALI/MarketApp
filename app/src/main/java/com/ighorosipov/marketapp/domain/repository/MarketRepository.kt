package com.ighorosipov.marketapp.domain.repository

import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.utils.Result

interface MarketRepository {

    suspend fun getItems(): Result<List<Item>>

    suspend fun getItemById(itemId: String): Item

    suspend fun insertItem(item: Item)

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun isUserSignIn(): Boolean

    suspend fun insertUserFavorite(favorite: Favorite)

    suspend fun findFavoriteById(itemId: String): Favorite?

    suspend fun deleteUserFavorite(itemId: String)

    suspend fun getUserFavorites(): List<String>

}