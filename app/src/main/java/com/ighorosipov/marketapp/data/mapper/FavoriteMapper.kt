package com.ighorosipov.marketapp.data.mapper

import com.ighorosipov.marketapp.data.model.db.FavoriteEntity
import com.ighorosipov.marketapp.domain.model.db.Favorite

class FavoriteMapper {

    fun mapFavoriteToFavoriteEntity(favorite: Favorite): FavoriteEntity = with(favorite) {
        FavoriteEntity(
            itemId = itemId
        )
    }

    fun mapFavoriteEntityToFavorite(favoriteEntity: FavoriteEntity): Favorite = with(favoriteEntity) {
        Favorite(
            id = id,
            itemId = itemId,
        )
    }

}