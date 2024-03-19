package com.ighorosipov.marketapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import com.ighorosipov.marketapp.utils.base.plusAssign
import com.ighorosipov.marketapp.utils.base.remove
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel @AssistedInject constructor(
    private val repository: MarketRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(repository.getUserFavorites())
        }
    }

    fun deleteFavorite(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(item.copy(isFavorite = !item.isFavorite))
            val deletedItem = items.value?.find { it == item }
            if (deletedItem != null) {
                _items.postValue(
                    items.value?.remove(deletedItem) ?: emptyList()
                )
            }
        }
    }

    fun toggleFavoriteAfterProductBackStack(itemId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = repository.getItemById(itemId)
            if (item.isFavorite && items.value?.contains(item) == false) {
                _items.plusAssign(item)
            } else if(!item.isFavorite){
                _items.postValue(items.value?.remove(item.copy(isFavorite = true)))
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): FavoriteViewModel

    }

}