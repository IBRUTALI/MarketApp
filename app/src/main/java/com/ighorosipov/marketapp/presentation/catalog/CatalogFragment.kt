package com.ighorosipov.marketapp.presentation.catalog

import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
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
    private val sortItems = arrayOf(
        Sort.Popularity("По популярности"),
        Sort.PriceDescending("По уменьшению цены"),
        Sort.PriceAscending("По возрастанию цены")
    )

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
            viewModel.changeSortDirection(sortItems[position])
            binding.items.scrollToPosition(0)
        }

        tagAdapter.setOnClickListener(object : TagAdapter.OnClickListener {
            override fun onTagClick(position: Int, tag: Tag) {
                viewModel.changeTag(tag)
            }

            override fun onClearClick(position: Int, tag: Tag) {
                viewModel.clearTag()
                tagAdapter.clearTag(position)
            }
        })

        itemAdapter.setOnClickListener(object : ItemAdapter.OnClickListener {

            override fun onItemClick(position: Int, item: Item) {
                findNavController().navigate(R.id.action_catalogFragment_to_productFragment)
            }

            override fun onHeartClick(position: Int, item: Item) {
                viewModel.toggleFavorite(item.id) {
                    itemAdapter.checkFavorites(position)
                }
            }
        })

    }

    private fun initSortAdapter() {
        sortAdapter = ArrayAdapter(requireContext(), R.layout.item_sort)
        sortItems.forEach {
            sortAdapter.add(it.type)
        }
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

        viewModel.sortDirection.observe(viewLifecycleOwner) { direction ->
            when (direction) {
                is Sort.Popularity -> {
                    binding.sortList.setText(direction.value, false)
                    viewModel.sort(direction)
                }

                is Sort.PriceDescending -> {
                    binding.sortList.setText(direction.value, false)
                    viewModel.sort(direction)
                }

                is Sort.PriceAscending -> {
                    binding.sortList.setText(direction.value, false)
                    viewModel.sort(direction)
                }
            }
        }

        viewModel.tag.observe(viewLifecycleOwner) { tag ->
                viewModel.filterByTag(tag)
                tagAdapter.updateTag(0, tag)
        }

    }

    override fun onPause() {
        super.onPause()
        binding.sortList.setText("", false)
    }
}