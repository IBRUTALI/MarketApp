package com.ighorosipov.marketapp.presentation.catalog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ighorosipov.marketapp.databinding.ItemTagBinding
import com.ighorosipov.marketapp.presentation.catalog.Tag

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var tags = listOf(
        Tag.All("Смотреть все", "all"),
        Tag.Face("Лицо", "face"),
        Tag.Body("Тело", "body"),
        Tag.Suntan("Загар", "suntan"),
        Tag.Mask("Массаж", "mask"),
    )

    private var currentTag = tags[0]

    class TagViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val isSelectedTag = tags[position] == currentTag
        with(holder.binding) {
            tag.text = tags[position].localString
            clear.visibility = if(isSelectedTag && tags[position] != tags[0]) {
                View.VISIBLE
            } else View.GONE
        }

        holder.itemView.isSelected = isSelectedTag

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onTagClick(position, tags[position])
            }
        }

        holder.binding.clear.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onClearClick(position, tags[position])
            }
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onTagClick(position: Int, tag: Tag)

        fun onClearClick(position: Int, tag: Tag)
    }

    fun updateTag(position: Int, tag: Tag) {
        val lastTagPosition = tags.indexOf(currentTag)
        val currentTagPosition = tags.indexOf(tag)
        currentTag = tag
        notifyItemChanged(currentTagPosition)
        notifyItemChanged(lastTagPosition, false)
    }

    fun clearTag(position: Int) {
        currentTag = tags[0]
        notifyItemChanged(position)
        notifyItemChanged(0, false)
    }

    fun setCurrentTag(tag: Tag) {
        currentTag = tag
        notifyItemChanged(0, false)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

}