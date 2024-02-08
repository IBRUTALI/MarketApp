package com.ighorosipov.marketapp.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Items
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.repository.ProductRepository
import com.ighorosipov.marketapp.domain.utils.Result
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    private val marketRepository: MarketRepository
) : BaseViewModel() {

    private val _items = MutableLiveData<Result<Items>>()
    val items: LiveData<Result<Items>> = _items

    private val _favorites = MutableLiveData<List<String>>(emptyList())
    val favorites: LiveData<List<String>> = _favorites

    init {
        getItems()
        getFavorites()
    }

    private fun getItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(Result.Loading())
            _items.postValue(productRepository.getProducts())
        }
    }

    fun toggleFavorite(id: String, function: () -> Unit) {
        viewModelScope.launch {
            val favorite = Favorite(
                itemId = id
            )
            if (marketRepository.findFavoriteById(id) == null) {
                _favorites.plusAssign(id)
                marketRepository.insertUserFavorite(
                    favorite
                )
                function.invoke()
            } else {
                _favorites.postValue(favorites.value?.remove(id))
                marketRepository.deleteUserFavorite(id)
                function.invoke()
            }

        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _favorites.postValue(marketRepository.getUserFavorites())
        }
    }

    fun sort() {

    }

    @AssistedFactory
    interface Factory {

        fun create(): CatalogViewModel

    }
}

operator fun <T> MutableLiveData<List<T>>.plusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}

private fun List<String>.remove(string: String): List<String> {
    val result = this.toMutableList()
    result.remove(string)
    return result
}
