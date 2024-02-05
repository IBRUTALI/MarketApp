package com.ighorosipov.marketapp.domain.repository

import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.model.db.User

interface MarketRepository {

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun isUserSignIn(): Boolean

    suspend fun insertUserFavorite(favorite: Favorite)

    suspend fun findFavoriteById(itemId: String): Favorite?

    suspend fun deleteUserFavorite(itemId: String)

    suspend fun getUserFavorites(): List<String>

}