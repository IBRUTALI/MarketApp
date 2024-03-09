package com.ighorosipov.marketapp.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.utils.Result
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatalogViewModel @AssistedInject constructor(
    private val marketRepository: MarketRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<Result<List<Item>>>()
    val items: LiveData<Result<List<Item>>> = _items

    private var initialItems: List<Item> = emptyList()

    private val _favorites = MutableLiveData<List<String>>(emptyList())
    val favorites: LiveData<List<String>> = _favorites

    private val _tag = MutableLiveData<Tag>()
    val tag: LiveData<Tag> = _tag

    private val _sortDirection = MutableLiveData<Sort>()
    val sortDirection: LiveData<Sort> = _sortDirection

    private var job: Job? = null

    init {
        getItems()
        getFavorites()
        _sortDirection.value = Sort.Default()
    }

    private fun getItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(Result.Loading())
            val items = marketRepository.getItems()
            _items.postValue(items)
            initialItems = items.data ?: emptyList()
        }
    }

    fun toggleFavorite(id: String, function: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                itemId = id
            )
            if (marketRepository.getFavoriteById(id) == null) {
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

    fun setFavorite(itemId: String, isFavorite: Boolean) {
        if (isFavorite) {
            _favorites.plusAssign(itemId)
        } else {
            _favorites.postValue(favorites.value?.remove(itemId))
        }
    }

    fun changeSortDirection(value: Sort) {
        _sortDirection.value = value
    }

    fun sort(direction: Sort, filteredItemsByTag: List<Item>? = null) {
        viewModelScope.launch(Dispatchers.Default) {
            val items = filteredItemsByTag ?: items.value?.data
            when (direction) {
                is Sort.Popularity -> {
                    _items.postValue(
                        Result.Success(items?.sortedByDescending {
                            it.feedback.rating
                        } ?: emptyList()))
                }

                is Sort.PriceDescending -> {
                    _items.postValue(
                        Result.Success(items?.sortedByDescending {
                            it.price.priceWithDiscount.toInt()
                        } ?: emptyList())
                    )

                }

                is Sort.PriceAscending -> {
                    _items.postValue(
                        Result.Success(items?.sortedBy {
                            it.price.priceWithDiscount.toInt()
                        } ?: emptyList())
                    )
                }

                is Sort.Default -> {}
            }
        }
    }

    fun changeTag(tag: Tag) {
        _tag.value = tag
    }

    fun clearTag() {
        _tag.value = Tag.All()
    }

    fun filterByTag(tag: Tag) {
        if (job != null) {
            job?.cancel()
            job = null
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            if (isActive.not()) return@launch
            when (tag) {

                is Tag.All -> {
                    sortDirection.value?.let { sort(it, initialItems) }
                }

                else -> {
                    initialItems.filter {
                        it.tags.contains(tag.tag)
                    }.also { list ->
                        sortDirection.value?.let { sort(it, list) }
                    }

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
