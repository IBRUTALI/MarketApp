package com.ighorosipov.marketapp.presentation.favorite

import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FavoriteViewModel @AssistedInject constructor(

) : BaseViewModel() {
    // TODO: Implement the ViewModel

    @AssistedFactory
    interface Factory {

        fun create(): FavoriteViewModel

    }

}