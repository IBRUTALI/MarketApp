package com.ighorosipov.marketapp.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ItemProductBinding
import com.ighorosipov.marketapp.domain.model.Item
import com.ighorosipov.marketapp.presentation.catalog.adapter.ItemAdapter.Companion.mapOfImages

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ItemViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var items = emptyList<Item>()

    class ItemViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.binding) {
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
            heart.setImageResource(R.drawable.ic_heart_fill)
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
        val diffUtil = FavoriteDiff(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        items = newList
    }

    override fun getItemCount(): Int {
        return items.size
    }
}