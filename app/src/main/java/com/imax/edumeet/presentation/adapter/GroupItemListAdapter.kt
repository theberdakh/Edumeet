package com.imax.edumeet.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ItemGroupBinding
import com.imax.edumeet.presentation.adapter.vh.GroupItemViewHolder
import com.imax.edumeet.presentation.models.group.GroupItem
import com.imax.edumeet.presentation.models.group.GroupItemCallback

class GroupItemListAdapter: ListAdapter<GroupItem, GroupItemListAdapter.GroupItemViewHolder>(GroupItemCallback) {

    private var selectedPosition = RecyclerView.NO_POSITION

    private var onGroupItemClickListener: ((GroupItem) -> Unit)? = null
    internal fun onGroupItemClickListener(block: ((GroupItem) -> Unit)){
        onGroupItemClickListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {
        return GroupItemViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) = holder.bind(position)

    inner class GroupItemViewHolder(private val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            if (position == selectedPosition) {
                binding.root.setBackgroundResource(R.drawable.bg_number_selected)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_number)
            }
            getItem(adapterPosition).let { groupItem ->
                binding.root.text = groupItem.name

                binding.root.setOnClickListener{
                    onGroupItemClickListener?.invoke(groupItem)
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                }
            }

        }
    }

}
