package com.ighorosipov.marketapp.data.repository

import com.ighorosipov.marketapp.data.dto.room.MarketDao
import com.ighorosipov.marketapp.data.mapper.FavoriteMapper
import com.ighorosipov.marketapp.data.mapper.UserMapper
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val dao: MarketDao
): MarketRepository {

    override suspend fun insertUser(user: User) {
        dao.insertUser(UserMapper().mapUserToUserEntity(user))
    }

    override suspend fun deleteUser(user: User) {
        dao.deleteUser(UserMapper().mapUserToUserEntity(user))
    }

    override suspend fun isUserSignIn(): Boolean {
        return dao.getUser() != null
    }

    override suspend fun insertUserFavorite(favorite: Favorite) {
        dao.insertUserFavorite(FavoriteMapper().mapFavoriteToFavoriteEntity(favorite))
    }

    override suspend fun findFavoriteById(itemId: String): Favorite? {
        return dao.findFavoriteById(itemId)
            ?.let { FavoriteMapper().mapFavoriteEntityToFavorite(it) }
    }

    override suspend fun deleteUserFavorite(itemId: String) {
        dao.deleteUserFavorite(itemId)
    }

    override suspend fun getUserFavorites(): List<String> {
        return dao.getUserFavorites()
    }

}