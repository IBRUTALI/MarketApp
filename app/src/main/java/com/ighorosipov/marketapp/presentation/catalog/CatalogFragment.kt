package com.ighorosipov.marketapp.presentation.catalog

import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentCatalogBinding
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.domain.utils.Result
import com.ighorosipov.marketapp.presentation.catalog.adapter.ItemAdapter
import com.ighorosipov.marketapp.presentation.catalog.adapter.TagAdapter
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel

class CatalogFragment : BaseFragment<FragmentCatalogBinding, CatalogViewModel>(
    FragmentCatalogBinding::inflate
) {
    private val tagAdapter by lazy { TagAdapter() }
    private val itemAdapter by lazy { ItemAdapter() }
    private lateinit var sortAdapter: ArrayAdapter<String>
    override val viewModel: CatalogViewModel by lazyViewModel {
        requireContext().appComponent().catalogViewModel().create()
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        initSortAdapter()
        initTagAdapter()
        initItemAdapter()
        adapterClickListeners()
    }

    private fun adapterClickListeners() {

        binding.sortList.onItemClickListener = OnItemClickListener { parent, view, position, id ->

        }

        tagAdapter.setOnClickListener(object : TagAdapter.OnClickListener {
            override fun onClick(position: Int, tag: String) {

            }
        })

        itemAdapter.setOnClickListener(object : ItemAdapter.OnClickListener {

            override fun onItemClick(position: Int, item: Item) {
                findNavController().navigate(R.id.action_catalogFragment_to_productFragment)
            }

            override fun onHeartClick(position: Int, item: Item) {
                viewModel.toggleFavorite(item.id) {
                    itemAdapter.checkFavorites(item.id, position)
                }
            }
        })

    }

    private fun initSortAdapter() {
        val sortItems = arrayOf("По популярности", "По уменьшению цены", "По возрастанию цены")
        sortAdapter = ArrayAdapter(requireContext(), R.layout.item_sort, sortItems)
        binding.sortList.setText(sortItems[0], TextView.BufferType.EDITABLE)
        binding.sortList.setAdapter(sortAdapter)
    }

    private fun initTagAdapter() {
        binding.tags.adapter = tagAdapter
    }

    private fun initItemAdapter() {
        binding.items.adapter = itemAdapter
    }

    override fun subscribeToObservers() {
        viewModel.items.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    itemAdapter.setList(result.data?.items ?: emptyList())
                }

                is Result.Error -> {

                }
            }
        }
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            itemAdapter.setFavorites(favorites)
        }

    }
}