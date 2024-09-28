package com.imax.edumeet.presentation.models

import androidx.recyclerview.widget.DiffUtil

data class FeedbackItem(
    val profileImage: String,
    val name: String,
    val rating: Float,
    val comment: String
)

object FeedbackItemDiffCallback: DiffUtil.ItemCallback<FeedbackItem>(){
    override fun areItemsTheSame(oldItem: FeedbackItem, newItem: FeedbackItem): Boolean {
        return oldItem.rating == newItem.rating && oldItem.comment == newItem.comment && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FeedbackItem, newItem: FeedbackItem): Boolean {
        return oldItem == newItem
    }

}
