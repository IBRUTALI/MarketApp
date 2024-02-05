package com.ighorosipov.marketapp.presentation.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ItemProductBinding
import com.ighorosipov.marketapp.domain.model.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var items = emptyList<Item>()
    private var favorites = emptyList<String>()


    class ItemViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.binding) {
            val imageList = mutableListOf<SlideModel>()
            mapOfImages[items[position].id]?.forEach {
                imageList.add(SlideModel(it))
            }
            "${items[position].price.price} ${items[position].price.unit}".apply {
                price.text = this
            }
            itemImage.setImageList(imageList)
            "${items[position].price.priceWithDiscount} ${items[position].price.unit}".apply {
                priceWithDiscount.text = this
            }
            "-${items[position].price.discount}%".apply { discount.text = this }
            title.text = items[position].title
            subtitle.text = items[position].subtitle
            reviews.text = items[position].feedback.rating.toString()
            "(${items[position].feedback.count})".apply {
                reviewsCount.text = this
            }
            if (favorites.contains(items[position].id)) {
                heart.setImageResource(R.drawable.ic_heart_fill)
            } else heart.setImageResource(R.drawable.ic_heart_empty)
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onClick(position, items[position])
            }
        }

        holder.binding.heart.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onClick(position, items[position])
            }
        }
    }

    fun setOnItemClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setOnHeartClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, item: Item)
    }

    fun setList(newList: List<Item>) {
        val diffUtil = ItemDiff(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        items = newList
    }

    fun checkFavorites(itemId: String, position: Int) {
        notifyItemChanged(position)
    }

    fun setFavorites(newList: List<String>) {
        favorites = newList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        val mapOfImages = mapOf<String, List<Int>>(
            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to listOf(R.drawable.six, R.drawable.five),
            "54a876a5-2205-48ba-9498-cfecff4baa6e" to listOf(R.drawable.one, R.drawable.two),
            "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to listOf(R.drawable.five, R.drawable.six),
            "16f88865-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.three, R.drawable.four),
            "26f88856-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.two, R.drawable.three),
            "15f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.six, R.drawable.one),
            "88f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.four, R.drawable.three),
            "55f58865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.one, R.drawable.five)
        )
    }

}