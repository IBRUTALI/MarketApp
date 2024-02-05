package com.ighorosipov.marketapp.presentation.account

import com.ighorosipov.marketapp.utils.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class AccountViewModel @AssistedInject constructor(

) : BaseViewModel() {
    // TODO: Implement the ViewModel


    @AssistedFactory
    interface Factory {

        fun create(): AccountViewModel

    }

}