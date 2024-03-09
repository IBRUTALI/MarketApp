package com.ighorosipov.marketapp.presentation.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ighorosipov.marketapp.databinding.ItemInfoBinding
import com.ighorosipov.marketapp.domain.model.Info

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {
    private var items = listOf<Info>()


    class InfoViewHolder(val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        with(holder.binding) {
            title.text = items[position].title
            subtitle.text = items[position].value
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<Info>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}