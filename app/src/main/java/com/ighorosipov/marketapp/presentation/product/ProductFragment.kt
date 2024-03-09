package com.ighorosipov.marketapp.presentation.product

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentProductBinding
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment.Companion.BUNDLE_ITEM_ID
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment.Companion.BUNDLE_ITEM_POSITION
import com.ighorosipov.marketapp.presentation.catalog.adapter.ItemAdapter.Companion.mapOfImages
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
            viewModel.toggleFavorite()
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
        binding.info.layoutManager = LinearLayoutManager(requireContext())
        binding.info.addItemDecoration(dividerItemDecoration)
    }

    private fun getBundle(): String? {
        val itemId = arguments?.getString(BUNDLE_ITEM_ID)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(BUNDLE_ITEM_ID, itemId)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(BUNDLE_ITEM_POSITION, itemId)
        return itemId
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
        viewModel.item.observe(viewLifecycleOwner) { item ->
            binding.apply {
                val imageList = mutableListOf<SlideModel>()
                mapOfImages[item.id]?.forEach {
                    imageList.add(SlideModel(it))
                }
                itemImage.setImageList(imageList)
                title.text = item.title
                subtitle.text = item.subtitle
                "${ContextCompat.getString(requireContext(), R.string.available)} ${item.available}".apply {
                    available.text = this
                }
                ratingBar.rating = item.feedback.rating.toFloat()
                reviews.text = item.feedback.rating.toString()
                "${item.feedback.count}".apply {
                    reviewsCount.text = this
                }
                "${item.price.priceWithDiscount} ${item.price.unit}".apply {
                    priceWithDiscount.text = this
                }
                "${item.price.price} ${item.price.unit}".apply {
                    price.text = this
                }

                "-${item.price.discount}%".apply {
                    discount.text = this
                }
                buttonBrand.setTitle(item.title)
                description.text = item.description
                infoAdapter.setList(item.info)
                ingredients.text = item.ingredients
                buttonAdd.setTitle(item.price.priceWithDiscount + " " + item.price.unit)
                buttonAdd.setTitleStrike(item.price.price + " " + item.price.unit)
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            if(it) {
                binding.heart.setImageResource(R.drawable.ic_heart_fill)
            } else {
                binding.heart.setImageResource(R.drawable.ic_heart_empty)
            }
            findNavController().previousBackStackEntry?.savedStateHandle?.set(CatalogFragment.BUNDLE_ITEM_IS_FAVORITE, it)
        }
    }

}