package com.ighorosipov.marketapp.presentation.catalog

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
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
    private lateinit var smoothScroller: SmoothScroller
    private var itemId: String? = null

    override val viewModel: CatalogViewModel by lazyViewModel {
        requireContext().appComponent().catalogViewModel().create()
    }

    private val sortItems = arrayOf(
        Sort.Popularity(),
        Sort.PriceDescending(),
        Sort.PriceAscending()
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
                val bundle = bundleOf(
                    BUNDLE_ITEM_ID to item.id
                )
                findNavController().navigate(R.id.action_catalogFragment_to_productFragment, bundle)
            }

            override fun onHeartClick(position: Int, item: Item) {
                viewModel.toggleFavorite(item)
            }
        })

    }

    private fun initSortAdapter() {
        sortAdapter = ArrayAdapter(requireContext(), R.layout.item_sort)
        sortItems.forEach {
            sortAdapter.add(requireContext().resources.getString(it.resId))
        }
        binding.sortList.setAdapter(sortAdapter)
    }

    private fun initTagAdapter() {
        binding.tags.adapter = tagAdapter
    }

    private fun initItemAdapter() {
        binding.items.adapter = itemAdapter
        smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }

    override fun subscribeToObservers() {

        viewModel.items.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    itemAdapter.setList(result.data ?: emptyList())
                }

                is Result.Error -> {
                    binding.loading.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.sortDirection.observe(viewLifecycleOwner) { direction ->
            binding.sortList.setText(requireContext().getString(direction.resId), false)
            viewModel.sort(direction)
            snapToTop()
        }

        viewModel.tag.observe(viewLifecycleOwner) { tag ->
            viewModel.filterByTag(tag)
            tagAdapter.updateTag(tag)
            snapToTop()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            BUNDLE_ITEM_ID
        )
            ?.observe(viewLifecycleOwner) { itemId ->
                this.itemId = itemId
                viewModel.toggleFavoriteAfterProductBackStack(itemId)
            }

    }

    private fun snapToTop() {
        binding.items.post {
            smoothScroller.targetPosition = itemAdapter.getFirstPosition()
            binding.items.layoutManager?.startSmoothScroll(smoothScroller)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.sortList.setText("", false)
    }

    companion object {
        const val BUNDLE_ITEM_ID = "BUNDLE_ITEM_ID"
    }
}