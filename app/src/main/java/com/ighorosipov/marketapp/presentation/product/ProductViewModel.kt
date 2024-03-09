package com.ighorosipov.marketapp.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.model.db.Favorite
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel @AssistedInject constructor(
    @Assisted private val itemId: String?,
    private val repository: MarketRepository
) : BaseViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

init {
    getItemById(itemId)
    isItemInFavorites(itemId)
}

    private fun getItemById(itemId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(itemId != null) {
                _item.postValue(repository.getItemById(itemId = itemId))
            }
        }
    }

    private fun isItemInFavorites(itemId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(itemId != null) {
                val isFavorite = repository.getFavoriteById(itemId = itemId) != null
                _isFavorite.postValue(isFavorite)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (itemId != null) {
                val favorite = Favorite(
                    itemId = itemId
                )
                if (repository.getFavoriteById(itemId) == null) {
                    _isFavorite.postValue(true)
                    repository.insertUserFavorite(
                        favorite
                    )
                } else {
                    withContext(Dispatchers.Main) {
                        _isFavorite.postValue(false)
                    }
                    repository.deleteUserFavorite(itemId)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted itemId: String?): ProductViewModel

    }

}