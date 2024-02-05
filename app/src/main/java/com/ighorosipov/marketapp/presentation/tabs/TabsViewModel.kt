package com.ighorosipov.marketapp.presentation.tabs

import com.ighorosipov.marketapp.presentation.login.LoginViewModel
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TabsViewModel @AssistedInject constructor(

) : BaseViewModel() {
    // TODO: Implement the ViewModel

    @AssistedFactory
    interface Factory {

        fun create(): TabsViewModel

    }
}