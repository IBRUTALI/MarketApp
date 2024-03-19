package com.ighorosipov.marketapp.data.mapper

import com.ighorosipov.marketapp.data.model.Feedback
import com.ighorosipov.marketapp.data.model.Info
import com.ighorosipov.marketapp.data.model.Item
import com.ighorosipov.marketapp.data.model.Price
import com.ighorosipov.marketapp.data.model.db.ItemEntity

class NetworkItemMapper {

   fun mapItemToData(item: Item): ItemEntity {
        return ItemEntity(
            available = item.available,
            description = item.description,
            feedback = mapFeedbackToData(item.feedback),
            id = item.id,
            info = item.info.map { mapInfoToData(it) },
            ingredients = item.ingredients,
            price = mapPriceToData(item.price),
            subtitle = item.subtitle,
            tags = item.tags,
            title = item.title,
            isFavorite = false
        )
    }

    private fun mapFeedbackToData(feedback: Feedback): Feedback {
        return Feedback(
            count = feedback.count,
            rating = feedback.rating
        )
    }

    private fun mapInfoToData(info: Info): Info {
        return Info(
            title = info.title,
            value = info.value
        )
    }

    private fun mapPriceToData(price: Price): Price {
        return Price(
            discount = price.discount,
            price = price.price,
            priceWithDiscount = price.priceWithDiscount,
            unit = price.unit
        )
    }

}