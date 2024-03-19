package com.ighorosipov.marketapp.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.utils.Result
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CatalogViewModel @AssistedInject constructor(
    private val repository: MarketRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<Result<List<Item>>>()
    val items: LiveData<Result<List<Item>>> = _items

    private var initialItems: List<Item> = emptyList()

    private val _tag = MutableLiveData<Tag>()
    val tag: LiveData<Tag> = _tag

    private val _sortDirection = MutableLiveData<Sort>()
    val sortDirection: LiveData<Sort> = _sortDirection

    private var job: Job? = null

    init {
        getItemsCached()
        _sortDirection.value = Sort.Default()
    }

    private fun getItemsCached() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(Result.Loading())
            val items = repository.getItemsCached()
            _items.postValue(items)
            initialItems = items.data ?: emptyList()
        }
    }

    fun toggleFavorite(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(item.copy(isFavorite = !item.isFavorite))
            _items.postValue(
                Result.Success(items.value?.data?.map {
                    if (it == item) {
                        it.copy(isFavorite = !item.isFavorite)
                    } else it
                } ?: emptyList())
            )
            initialItems = initialItems.map {
                if (it == item) {
                    it.copy(isFavorite = !item.isFavorite)
                } else it
            }
        }
    }

    fun toggleFavoriteAfterProductBackStack(itemId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = repository.getItemById(itemId)
            _items.postValue(
                Result.Success(items.value?.data?.map {
                    if (it.id == itemId) {
                        it.copy(isFavorite = item.isFavorite)
                    } else it
                } ?: emptyList())
            )
            initialItems = initialItems.map {
                if (it.id == itemId) {
                    it.copy(isFavorite = item.isFavorite)
                } else it
            }
        }
    }

    fun changeSortDirection(value: Sort) {
        _sortDirection.value = value
    }

    fun sort(direction: Sort, filteredItemsByTag: List<Item>? = null) {
        viewModelScope.launch(Dispatchers.IO) {
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

                is Sort.Default -> {
                    _items.postValue(
                        Result.Success(items ?: emptyList())
                    )
                }
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
