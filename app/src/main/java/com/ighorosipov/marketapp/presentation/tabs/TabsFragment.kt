package com.ighorosipov.marketapp.presentation.tabs

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentTabsBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class TabsFragment : BaseFragment<FragmentTabsBinding, TabsViewModel>(
    FragmentTabsBinding::inflate
) {
    override val viewModel: TabsViewModel by lazyViewModel {
        requireContext().appComponent().tabsViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun subscribeToObservers() {}


}