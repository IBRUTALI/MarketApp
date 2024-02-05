package com.ighorosipov.marketapp.presentation.account

import com.ighorosipov.marketapp.databinding.FragmentAccountBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>(
    FragmentAccountBinding::inflate
) {
    override val viewModel: AccountViewModel by lazyViewModel {
        requireContext().appComponent().accountViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {

    }

    override fun subscribeToObservers() {

    }


}