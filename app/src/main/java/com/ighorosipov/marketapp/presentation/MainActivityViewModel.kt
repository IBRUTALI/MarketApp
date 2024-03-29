package com.ighorosipov.marketapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel @AssistedInject constructor(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val _isSignIn= MutableLiveData<Boolean>()
    val isSignIn: LiveData<Boolean> = _isSignIn

    init {
        isSignIn()
    }

    private fun isSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _isSignIn.postValue(marketRepository.isUserSignIn())
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): MainActivityViewModel

    }

}