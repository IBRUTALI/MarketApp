package com.ighorosipov.marketapp.presentation.favorite

import com.ighorosipov.marketapp.databinding.FragmentFavoriteBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate
) {
    override val viewModel: FavoriteViewModel by lazyViewModel {
        requireContext().appComponent().favoriteViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {

    }

    override fun subscribeToObservers() {

    }

}