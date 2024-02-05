package com.ighorosipov.marketapp.data.mapper

import com.ighorosipov.marketapp.data.model.Feedback
import com.ighorosipov.marketapp.data.model.Info
import com.ighorosipov.marketapp.data.model.Item
import com.ighorosipov.marketapp.data.model.Price
import com.ighorosipov.marketapp.data.model.Items

class ProductMapper {

    fun mapItemsToData(product: com.ighorosipov.marketapp.domain.model.Items): Items =
        with(product.items) {
            com.ighorosipov.marketapp.data.model.Items(
                items = map { item ->
                    Item(
                        available = item.available,
                        description = item.description,
                        feedback = mapFeedbackToData(item.feedback),
                        id = item.id,
                        info = item.info.map { mapInfoToData(it) },
                        ingredients = item.ingredients,
                        price = mapPriceToData(item.price),
                        subtitle = item.subtitle,
                        tags = item.tags,
                        title = item.title
                    )
                }
            )
        }

    private fun mapFeedbackToData(feedback: com.ighorosipov.marketapp.domain.model.Feedback): Feedback {
        return Feedback(
            count = feedback.count,
            rating = feedback.rating
        )
    }

    private fun mapInfoToData(info: com.ighorosipov.marketapp.domain.model.Info): Info {
        return Info(
            title = info.title,
            value = info.value
        )
    }

    private fun mapPriceToData(price: com.ighorosipov.marketapp.domain.model.Price): Price {
        return Price(
            discount = price.discount,
            price = price.price,
            priceWithDiscount = price.priceWithDiscount,
            unit = price.unit
        )
    }

    fun mapItemsToDomain(product: Items): com.ighorosipov.marketapp.domain.model.Items =
        with(product.items) {
            com.ighorosipov.marketapp.domain.model.Items(
                items = map { item ->
                    com.ighorosipov.marketapp.domain.model.Item(
                        available = item.available,
                        description = item.description,
                        feedback = mapFeedbackToDomain(item.feedback),
                        id = item.id,
                        info = item.info.map { mapInfoToDomain(it) },
                        ingredients = item.ingredients,
                        price = mapPriceToDomain(item.price),
                        subtitle = item.subtitle,
                        tags = item.tags,
                        title = item.title
                    )
                }
            )
        }

    private fun mapFeedbackToDomain(feedback: Feedback): com.ighorosipov.marketapp.domain.model.Feedback {
        return com.ighorosipov.marketapp.domain.model.Feedback(
            count = feedback.count,
            rating = feedback.rating
        )
    }

    private fun mapInfoToDomain(info: Info): com.ighorosipov.marketapp.domain.model.Info {
        return com.ighorosipov.marketapp.domain.model.Info(
            title = info.title,
            value = info.value
        )
    }

    private fun mapPriceToDomain(price: Price):com.ighorosipov.marketapp.domain.model. Price {
        return com.ighorosipov.marketapp.domain.model.Price(
            discount = price.discount,
            price = price.price,
            priceWithDiscount = price.priceWithDiscount,
            unit = price.unit
        )
    }

}