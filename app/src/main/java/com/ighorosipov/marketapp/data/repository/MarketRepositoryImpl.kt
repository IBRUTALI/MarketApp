package com.ighorosipov.marketapp.data.repository

import com.ighorosipov.marketapp.data.dto.network.ProductApi
import com.ighorosipov.marketapp.data.dto.room.MarketDao
import com.ighorosipov.marketapp.data.mapper.FavoriteMapper
import com.ighorosipov.marketapp.data.mapper.ProductMapper
import com.ighorosipov.marketapp.data.mapper.UserMapper
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.Items
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.repository.ProductRepository
import com.ighorosipov.marketapp.domain.utils.Result
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val dao: MarketDao,
    private val api: ProductApi
): MarketRepository, ProductRepository {

    override suspend fun getItems(): Result<List<Item>> {
        return try {
            val getFromNetwork = api.getProducts().body()!!
            getFromNetwork.items.forEach { item ->
                dao.insertItem(item)
            }
            Result.Success(
                data = ProductMapper().mapListOfItemToDomain(dao.getItems())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getItemById(itemId: String): Item {
        return ProductMapper().mapItemToDomain(dao.findItemById(itemId))
    }

    override suspend fun insertItem(item: Item) {
        dao.insertItem(ProductMapper().mapItemToData(item))
    }

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

    override suspend fun getProducts(): Result<Items> {
        return try {
            Result.Success(
                data = ProductMapper().mapItemsToDomain(api.getProducts().body()!!)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = e.message ?: "An unknown error occurred.")
        }
    }

}