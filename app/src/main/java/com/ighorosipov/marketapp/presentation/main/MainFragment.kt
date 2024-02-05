package com.ighorosipov.marketapp.presentation.main

import com.ighorosipov.marketapp.databinding.FragmentMainBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by lazyViewModel {
        requireContext().appComponent().mainViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {

    }

    override fun subscribeToObservers() {

    }

}