package com.ighorosipov.marketapp.presentation.favorite

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentFavoriteBinding
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment
import com.ighorosipov.marketapp.presentation.favorite.adapter.FavoriteAdapter
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate
) {
    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override val viewModel: FavoriteViewModel by lazyViewModel {
        requireContext().appComponent().favoriteViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        initItemAdapter()
        favoriteAdapterClickListener()
    }

    private fun favoriteAdapterClickListener() {
        favoriteAdapter.setOnClickListener(object : FavoriteAdapter.OnClickListener {

            override fun onItemClick(position: Int, item: Item) {
                val bundle = bundleOf(
                    CatalogFragment.BUNDLE_ITEM_ID to item.id
                )
                findNavController().navigate(R.id.action_favoriteFragment_to_productFragment2, bundle)
            }

            override fun onHeartClick(position: Int, item: Item) {
                viewModel.deleteFavorite(item)
            }
        })
    }

    private fun initItemAdapter() {
        binding.favItems.adapter = favoriteAdapter
    }

    override fun subscribeToObservers() {

        viewModel.items.observe(viewLifecycleOwner) { items ->
            favoriteAdapter.setList(items)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

}