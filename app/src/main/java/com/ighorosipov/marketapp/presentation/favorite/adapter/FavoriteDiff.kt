package com.ighorosipov.marketapp.presentation.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ighorosipov.marketapp.domain.model.Item

class FavoriteDiff(
    private val oldList: List<Item>,
    private val newList: List<Item>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            oldList[oldItemPosition].available != newList[newItemPosition].available -> {
                false
            }

            oldList[oldItemPosition].description != newList[newItemPosition].description -> {
                false
            }

            oldList[oldItemPosition].feedback != newList[newItemPosition].feedback -> {
                false
            }

            oldList[oldItemPosition].info != newList[newItemPosition].info -> {
                false
            }

            oldList[oldItemPosition].ingredients != newList[newItemPosition].ingredients -> {
                false
            }

            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }

            oldList[oldItemPosition].subtitle != newList[newItemPosition].subtitle -> {
                false
            }

            oldList[oldItemPosition].tags != newList[newItemPosition].tags -> {
                false
            }

            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }

            oldList[oldItemPosition].isFavorite != newList[newItemPosition].isFavorite -> {
                false
            }

            else -> true
        }
    }
}