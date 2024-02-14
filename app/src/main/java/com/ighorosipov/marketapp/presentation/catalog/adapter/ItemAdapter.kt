package com.ighorosipov.marketapp.presentation.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ItemProductBinding
import com.ighorosipov.marketapp.domain.model.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var items = emptyList<Item>()
    private var favorites = emptyList<String>()
    private var firstPosition = 0


    class ItemViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.binding) {
            if (holder.adapterPosition == 0) {
                firstPosition = position
            }
            val imageList = mutableListOf<SlideModel>()
            mapOfImages[items[holder.adapterPosition].id]?.forEach {
                imageList.add(SlideModel(it))
            }
            "${items[holder.adapterPosition].price.price} ${items[holder.adapterPosition].price.unit}".apply {
                price.text = this
            }
            itemImage.setImageList(imageList)
            "${items[holder.adapterPosition].price.priceWithDiscount} ${items[holder.adapterPosition].price.unit}".apply {
                priceWithDiscount.text = this
            }
            "-${items[holder.adapterPosition].price.discount}%".apply {
                discount.text = this
            }
            title.text = items[holder.adapterPosition].title
            subtitle.text = items[holder.adapterPosition].subtitle
            reviews.text = items[holder.adapterPosition].feedback.rating.toString()
            "(${items[holder.adapterPosition].feedback.count})".apply {
                reviewsCount.text = this
            }
            if (favorites.contains(items[holder.adapterPosition].id)) {
                heart.setImageResource(R.drawable.ic_heart_fill)
            } else heart.setImageResource(R.drawable.ic_heart_empty)
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onItemClick(holder.adapterPosition, items[holder.adapterPosition])
            }
        }

        holder.binding.heart.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onHeartClick(holder.adapterPosition, items[holder.adapterPosition])
            }
        }

        holder.binding.itemImage.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                if (onClickListener != null) {
                    onClickListener?.onItemClick(
                        holder.adapterPosition,
                        items[holder.adapterPosition]
                    )
                }
            }

            override fun doubleClick(position: Int) {}
        })

        holder.binding.add.setOnClickListener {}

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onItemClick(position: Int, item: Item)
        fun onHeartClick(position: Int, item: Item)

    }

    fun setList(newList: List<Item>) {
        val diffUtil = ItemDiff(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        items = newList
    }

    fun checkFavorites(position: Int) {
        notifyItemChanged(position, null)
    }

    fun setFavorites(newList: List<String>) {
        favorites = newList
    }

    fun getFirstPosition(): Int {
        return firstPosition
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