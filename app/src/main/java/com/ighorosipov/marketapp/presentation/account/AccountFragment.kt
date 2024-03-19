package com.ighorosipov.marketapp.presentation.account

import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentAccountBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.base.findTopNavController
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
        binding.buttonUser.setOnClickListener {}
        binding.buttonFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_favoriteFragment)
        }
        binding.buttonMarkets.setOnClickListener {}
        binding.buttonFeedback.setOnClickListener {}
        binding.buttonOffer.setOnClickListener {}
        binding.buttonPurchaseReturns.setOnClickListener {}
        binding.buttonExit.setOnClickListener {
            viewModel.logOut()
        }
    }

    override fun subscribeToObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                user?.let {
                    buttonUser.setTitle(user.firstname + " " + user.lastname)
                    buttonUser.setSubtitle(user.number)
                }
            }
        }

        viewModel.logOutEvent.observe(viewLifecycleOwner) {
            findTopNavController().navigate(R.id.loginFragment,null, navOptions {
            popUpTo(R.id.tabsFragment) {
                inclusive = true
            }
        })
        }

        viewModel.favoriteCount.observe(viewLifecycleOwner) { count ->
            if (count != 0) {
                binding.buttonFavorites.setSubtitle(count.toString() + " " + requireContext().resources.getQuantityString(R.plurals.favorite_plurals, count))
            } else {
                binding.buttonFavorites.setSubtitle("")
            }
        }

    }


}