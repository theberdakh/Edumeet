package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ItemGroupBinding
import com.imax.edumeet.presentation.models.group.GroupItem

class GroupItemViewHolder(private val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root) {
    private var selectedPosition = RecyclerView.NO_POSITION

    fun bind(item: GroupItem, onClick: ((GroupItem, Int) -> Unit)?, position: Int) {
        binding.root.text = item.name

        binding.root.setOnClickListener{
            selectedPosition = position
            binding.root.setBackgroundResource(R.drawable.bg_number_selected)
            onClick?.invoke(item, selectedPosition)
        }
    }
}
