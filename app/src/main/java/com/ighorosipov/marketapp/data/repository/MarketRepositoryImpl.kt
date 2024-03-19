package com.ighorosipov.marketapp.data.repository

import com.ighorosipov.marketapp.data.dto.network.ProductApi
import com.ighorosipov.marketapp.data.dto.room.MarketDao
import com.ighorosipov.marketapp.data.mapper.ItemMapper
import com.ighorosipov.marketapp.data.mapper.NetworkItemMapper
import com.ighorosipov.marketapp.data.mapper.UserMapper
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.utils.Result
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val dao: MarketDao,
    private val api: ProductApi,
) : MarketRepository {

    override suspend fun getItemsCached(): Result<List<Item>> {
        return try {
            val getFromNetwork = api.getProducts().body()!!
            val favoritesList = dao.getUserFavoritesIds()
            getFromNetwork.items.forEach { item ->
                var entityItem = NetworkItemMapper().mapItemToData(item)
                if (favoritesList.contains(entityItem.id)) {
                    entityItem = entityItem.copy(isFavorite = true)
                }
                dao.insertItem(entityItem)
            }
            Result.Success(
                data = ItemMapper().mapListOfItemToDomain(dao.getItems())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getItems(): Result<List<Item>> {
        return try {
            Result.Success(
                data = ItemMapper().mapListOfItemToDomain(dao.getItems())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getItemById(itemId: String): Item {
        return ItemMapper().mapDataToDomain(dao.getItemById(itemId))
    }

    override suspend fun updateItem(item: Item) {
        dao.updateItem(ItemMapper().mapDomainToData(item))
    }

    override suspend fun insertItem(item: Item) {
        dao.insertItem(ItemMapper().mapDomainToData(item))
    }

    override suspend fun insertUser(user: User) {
        dao.insertUser(UserMapper().mapUserToUserEntity(user))
    }

    override suspend fun getUser(): User? {
        return dao.getUser()?.let { UserMapper().mapUserEntityToUser(it) }
    }

    override suspend fun deleteUser(user: User) {
        dao.deleteUser(UserMapper().mapUserToUserEntity(user))
    }

    override suspend fun isUserSignIn(): Boolean {
        return dao.getUser() != null
    }

    override suspend fun getFavoritesCount(): Int {
        return dao.getUserFavoritesCount()
    }

    override suspend fun getUserFavorites(): List<Item> {
        return ItemMapper().mapListOfItemToDomain(dao.getUserFavorites())
    }

}