package com.ighorosipov.marketapp.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ighorosipov.marketapp.domain.model.db.User
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AccountViewModel @AssistedInject constructor(
    val repository: MarketRepository
) : BaseViewModel() {

    private val _logOutEvent = MutableLiveData<Unit>()
    val logOutEvent: LiveData<Unit> = _logOutEvent

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _favoriteCount = MutableLiveData<Int>()
    val favoriteCount: LiveData<Int> = _favoriteCount

    init {
        getUser()
        getFavoriteCount()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(repository.getUser())
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            if (user.value != null) {
                _logOutEvent.postValue(repository.deleteUser(user.value!!))
                _user.postValue(null)
            } else {
                return@launch
            }
        }
    }

    fun getFavoriteCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteCount.postValue(repository.getFavoritesCount())
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): AccountViewModel

    }

}