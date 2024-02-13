package com.ighorosipov.marketapp.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
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
import kotlinx.coroutines.withContext

class CatalogViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    private val marketRepository: MarketRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<Result<Items>>()
    val items: LiveData<Result<Items>> = _items

    private var initialItems: List<Item> = emptyList()

    private val _favorites = MutableLiveData<List<String>>(emptyList())
    val favorites: LiveData<List<String>> = _favorites

    private val _tag = MutableLiveData<Tag>()
    val tag: LiveData<Tag> = _tag

    private val _sortDirection = MutableLiveData<Sort>()
    val sortDirection: LiveData<Sort> = _sortDirection

    init {
        _sortDirection.value = Sort.Popularity("По популярности")
        getItems()
        getFavorites()
    }

    private fun getItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(Result.Loading())
            val items = productRepository.getProducts()
            _items.postValue(items)
            initialItems = items.data?.items ?: emptyList()
        }
    }

    fun toggleFavorite(id: String, function: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                itemId = id
            )
            if (marketRepository.findFavoriteById(id) == null) {
                _favorites.plusAssign(id)
                marketRepository.insertUserFavorite(
                    favorite
                )
                withContext(Dispatchers.Main) {
                    function.invoke()
                }
            } else {
                withContext(Dispatchers.Main) {
                    _favorites.postValue(favorites.value?.remove(id))
                    function.invoke()
                }
                marketRepository.deleteUserFavorite(id)
            }

        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _favorites.postValue(marketRepository.getUserFavorites())
        }
    }

    fun changeSortDirection(value: Sort) {
        _sortDirection.value = value
    }

    fun sort(direction: Sort) {
        viewModelScope.launch(Dispatchers.Default) {
            when (direction) {
                is Sort.Popularity -> {
                    _items.postValue(
                        Result.Success(Items(items.value?.data?.items?.sortedByDescending {
                            it.feedback.rating
                        } ?: emptyList()))
                    )
                }

                is Sort.PriceDescending -> {
                    _items.postValue(
                        Result.Success(Items(items.value?.data?.items?.sortedByDescending {
                            it.price.priceWithDiscount.toInt()
                        } ?: emptyList()))
                    )

                }

                is Sort.PriceAscending -> {
                    _items.postValue(
                        Result.Success(Items(items.value?.data?.items?.sortedBy {
                            it.price.priceWithDiscount.toInt()
                        } ?: emptyList()))
                    )
                }
            }
        }
    }

    fun changeTag(tag: Tag) {
        _tag.value = tag
    }

    fun clearTag() {
        _tag.value = Tag.All("Смотреть все", "all")
    }

    fun filterByTag(tag: Tag) {
        viewModelScope.launch(Dispatchers.IO) {
            when (tag) {

                is Tag.All -> {
                    _items.postValue(Result.Success(Items(initialItems)))
                    sortDirection.value?.let { sort(it) }
                }

                else -> {
                    _items.postValue(Result.Success(Items(initialItems.filter {
                        it.tags.contains(tag.tag)
                    })))
                    sortDirection.value?.let { sort(it) }
                }

            }
        }

    }

    @AssistedFactory
    interface Factory {

        fun create(): CatalogViewModel

    }
}

operator fun <T> MutableLiveData<List<T>>.plusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.postValue(value + listOf(item))
}

private fun List<String>.remove(string: String): List<String> {
    val result = this.toMutableList()
    result.remove(string)
    return result
}
