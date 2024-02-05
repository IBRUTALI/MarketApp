package com.ighorosipov.marketapp.presentation.product

import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ProductViewModel @AssistedInject constructor(

) : BaseViewModel() {
    // TODO: Implement the ViewModel

    @AssistedFactory
    interface Factory {

        fun create(): ProductViewModel

    }

}