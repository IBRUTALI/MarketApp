package com.ighorosipov.marketapp.presentation.product

import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment.Companion.BUNDLE_ITEM_ID
import com.ighorosipov.marketapp.presentation.catalog.adapter.ItemAdapter.Companion.mapOfImages
import com.ighorosipov.marketapp.presentation.product.adapter.InfoAdapter
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel


class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>(
    FragmentProductBinding::inflate
) {
    private val infoAdapter by lazy { InfoAdapter() }
    private var itemId: String? = null
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
            viewModel.changeDescriptionState()
        }
        binding.descriptionDetails.setOnClickListener {
            viewModel.changeDescriptionState()
        }
        binding.ingredientsCopy.setOnClickListener {

        }
        binding.ingredientsHide.setOnClickListener {
            viewModel.changeIngredientState()
        }
        binding.ingredientsDetails.setOnClickListener {
            viewModel.changeIngredientState()
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
        itemId = arguments?.getString(BUNDLE_ITEM_ID)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(BUNDLE_ITEM_ID, itemId)
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
                if(item.isFavorite) {
                    binding.heart.setImageResource(R.drawable.ic_heart_fill)
                } else {
                    binding.heart.setImageResource(R.drawable.ic_heart_empty)
                }
                val imageList = mutableListOf<SlideModel>()
                mapOfImages[item.id]?.forEach {
                    imageList.add(SlideModel(it))
                }
                itemImage.setImageList(imageList)
                title.text = item.title
                subtitle.text = item.subtitle
                val availableText = requireContext().resources.getQuantityString(R.plurals.available_plurals, item.available)
                "${ContextCompat.getString(requireContext(), R.string.available)} ${item.available} $availableText".apply {
                    available.text = this
                }

                if (item.feedback == null) {
                    feedbackGroup.visibility = View.GONE
                } else {
                    feedbackGroup.visibility = View.VISIBLE
                    ratingBar.rating = item.feedback.rating.toFloat()
                    reviews.text = item.feedback.rating.toString()
                    val feedbackText = requireContext().resources.getQuantityString(R.plurals.feedback_plurals, item.feedback.count)
                    "${item.feedback.count} $feedbackText".apply {
                        reviewsCount.text = this
                    }
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
        viewModel.descriptionState.observe(viewLifecycleOwner) { state ->
           when(state) {
               is VisibilityState.VISIBLE -> {
                   binding.apply {
                       descriptionDetails.visibility = View.GONE
                       descriptionHide.visibility = View.VISIBLE
                       description.visibility = View.VISIBLE
                   }
               }
               is VisibilityState.GONE -> {
                   binding.apply {
                       descriptionDetails.visibility = View.VISIBLE
                       descriptionHide.visibility = View.GONE
                       description.visibility = View.GONE
                   }
               }
           }
        }
        viewModel.ingredientState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is VisibilityState.VISIBLE -> {
                    binding.apply {
                        ingredientsDetails.visibility = View.VISIBLE
                        ingredientsHide.visibility = View.GONE
                        ingredients.maxLines = 2
                        ingredients.ellipsize = TextUtils.TruncateAt.END
                    }
                }
                is VisibilityState.GONE -> {
                    binding.apply {
                        ingredientsDetails.visibility = View.GONE
                        ingredientsHide.visibility = View.VISIBLE
                        ingredients.maxLines = 100
                    }
                }
            }
        }
    }



}