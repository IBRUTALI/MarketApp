package com.ighorosipov.marketapp.presentation.product

import com.ighorosipov.marketapp.domain.repository.ProductRepository
import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ProductViewModel @AssistedInject constructor(
    @Assisted private val itemId: Int?,
    private val repository: ProductRepository
) : BaseViewModel() {


    @AssistedFactory
    interface Factory {

        fun create(@Assisted itemId: Int?): ProductViewModel

    }

}