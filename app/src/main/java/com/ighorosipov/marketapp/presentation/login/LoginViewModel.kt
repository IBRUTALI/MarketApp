package com.ighorosipov.marketapp.presentation.login

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
import kotlinx.coroutines.withContext

class LoginViewModel @AssistedInject constructor(
    private val marketRepository: MarketRepository
) : BaseViewModel() {

    private val _startUserValues = MutableLiveData<User?>()
    val startUserValues: LiveData<User?> = _startUserValues

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _inputErrors = MutableLiveData<Set<UserInputError>>()
    val inputErrors: LiveData<Set<UserInputError>> = _inputErrors

    private val regexString = Regex("^[А-Я]?([а-я])*")
    private val setOfErrors = mutableSetOf<UserInputError>()

    init {
        _user.value = User(
            id = 0,
            firstname = "",
            lastname = "",
            number = ""
        )
        setOfErrors.add(UserInputError.EmptyFields)
        _inputErrors.postValue(setOfErrors)
    }

    fun insertUser() {
        viewModelScope.launch(Dispatchers.IO) {
            user.value?.let { marketRepository.insertUser(it) }
        }
    }

    fun setFirstnameChanges(firstname: String) {
        user.value?.let {
            val isMatch = regexString.matches(firstname)
            if (isMatch && firstname.isNotEmpty()) {
                setOfErrors.remove(UserInputError.InvalidFirstname)
                _inputErrors.postValue(setOfErrors)
                _user.postValue(
                    it.copy(firstname = firstname.trim())
                )
            } else _inputErrors.postValue(
                setOfErrors.apply { add(UserInputError.InvalidFirstname) }
            )
        }
    }

    fun setLastnameChanges(lastname: String) {
        user.value?.let {
            val isMatch = regexString.matches(lastname)
            if (isMatch && lastname.isNotEmpty()) {
                setOfErrors.remove(UserInputError.InvalidLastname)
                _inputErrors.postValue(setOfErrors)
                _user.postValue(
                    it.copy(lastname = lastname.trim())
                )
            } else _inputErrors.postValue(
                setOfErrors.apply { add(UserInputError.InvalidLastname) }
            )
        }
    }

    fun setNumberChanges(number: String) {
        user.value?.let {
            if (number.length == 16) {
                setOfErrors.remove(UserInputError.InvalidNumber)
                _inputErrors.postValue(setOfErrors)
                _user.postValue(
                    it.copy(number = number)
                )
            } else {
                setOfErrors.add(UserInputError.InvalidNumber)
                _inputErrors.postValue(setOfErrors)
            }

        }
    }

    fun isFieldsNotEmpty(firstname: String, lastname: String, number: String): Boolean {
        return if (firstname.isNotBlank() && lastname.isNotBlank() && number.length == 16) {
            setOfErrors.remove(UserInputError.EmptyFields)
            _inputErrors.postValue(setOfErrors)
            true
        } else {
            setOfErrors.add(UserInputError.EmptyFields)
            _inputErrors.postValue(setOfErrors)
            false
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): LoginViewModel

    }

}