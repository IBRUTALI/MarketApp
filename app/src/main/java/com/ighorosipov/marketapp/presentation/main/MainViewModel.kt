package com.ighorosipov.marketapp.presentation.main

import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MainViewModel @AssistedInject constructor(

) : BaseViewModel() {
    // TODO: Implement the ViewModel

    @AssistedFactory
    interface Factory {

        fun create(): MainViewModel

    }

}