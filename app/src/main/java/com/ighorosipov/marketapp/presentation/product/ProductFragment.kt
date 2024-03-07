package com.ighorosipov.marketapp.presentation.product

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentProductBinding
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment.Companion.BUNDLE_ITEM_ID
import com.ighorosipov.marketapp.presentation.product.adapter.InfoAdapter
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel


class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>(
    FragmentProductBinding::inflate
) {
    private val infoAdapter by lazy { InfoAdapter() }

    override val viewModel: ProductViewModel by lazyViewModel {
        requireContext().appComponent().productViewModel().create(getBundle())
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        binding.heart.setOnClickListener {

        }
        binding.question.setOnClickListener {

        }
        binding.buttonBrand.setOnClickListener {

        }
        binding.descriptionHide.setOnClickListener {

        }
        binding.descriptionDetails.setOnClickListener {

        }
        binding.ingredientsCopy.setOnClickListener {

        }
        binding.ingredientsHide.setOnClickListener {

        }
        binding.ingredientsDetails.setOnClickListener {

        }
        binding.buttonAdd.setOnClickListener {

        }
        initMenu()
        initInfoAdapter()
    }

    private fun initInfoAdapter() {
        binding.info.adapter = infoAdapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.info.context,
            LinearLayoutManager.VERTICAL
        )
        binding.info.addItemDecoration(dividerItemDecoration)
    }

    private fun getBundle(): Int? {
        return arguments?.getInt(BUNDLE_ITEM_ID)
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.basket_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.add_to_basket -> {}
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun subscribeToObservers() {

    }

}