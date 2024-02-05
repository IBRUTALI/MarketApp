package com.ighorosipov.marketapp.presentation.catalog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ighorosipov.marketapp.databinding.ItemTagBinding

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var tags = listOf("All", "Face", "Body", "Suntan", "Mask")

    class TagViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        with(holder.binding) {
            tag.text = tags[position]
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onClick(position, tags[position])
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, tag: String)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<String>) {
        tags = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tags.size
    }

}