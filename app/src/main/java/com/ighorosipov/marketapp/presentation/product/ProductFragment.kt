package com.ighorosipov.marketapp.presentation.product

import com.ighorosipov.marketapp.databinding.FragmentProductBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>(
    FragmentProductBinding::inflate
) {
    override val viewModel: ProductViewModel by lazyViewModel {
        requireContext().appComponent().productViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                ratingBar.rating = 2.3f
            }
        }
    }

    override fun subscribeToObservers() {

    }

}