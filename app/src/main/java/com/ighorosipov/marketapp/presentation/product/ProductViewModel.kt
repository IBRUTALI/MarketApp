package com.ighorosipov.marketapp.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel @AssistedInject constructor(
    @Assisted private val itemId: String?,
    private val repository: MarketRepository
) : BaseViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    private val _descriptionState = MutableLiveData<VisibilityState>(VisibilityState.VISIBLE)
    val descriptionState: LiveData<VisibilityState> = _descriptionState

    private val _ingredientState = MutableLiveData<VisibilityState>(VisibilityState.GONE)
    val ingredientState: LiveData<VisibilityState> = _ingredientState

init {
    getItemById(itemId)
}

    private fun getItemById(itemId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(itemId != null) {
                _item.postValue(repository.getItemById(itemId = itemId))
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            item.value?.let {
                val newItem = it.copy(isFavorite = !it.isFavorite)
                repository.updateItem(newItem)
                _item.postValue(newItem)
            }
        }
    }

    fun changeDescriptionState() {
        when(descriptionState.value!!) {
            is VisibilityState.VISIBLE -> {
                _descriptionState.value = VisibilityState.GONE
            }
            is VisibilityState.GONE -> {
                _descriptionState.value = VisibilityState.VISIBLE
            }
        }
    }

    fun changeIngredientState() {
        when(ingredientState.value!!) {
            is VisibilityState.VISIBLE -> {
                _ingredientState.value = VisibilityState.GONE
            }
            is VisibilityState.GONE -> {
                _ingredientState.value = VisibilityState.VISIBLE
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted itemId: String?): ProductViewModel

    }

}