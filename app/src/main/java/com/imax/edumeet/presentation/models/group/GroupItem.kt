package com.imax.edumeet.presentation.models.group

import androidx.recyclerview.widget.DiffUtil

data class GroupItem(
    val id: String,
    val name: String
)

object GroupItemCallback: DiffUtil.ItemCallback<GroupItem>(){
    override fun areItemsTheSame(oldItem: GroupItem, newItem: GroupItem): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GroupItem, newItem: GroupItem): Boolean {
        return oldItem == newItem
    }

}
