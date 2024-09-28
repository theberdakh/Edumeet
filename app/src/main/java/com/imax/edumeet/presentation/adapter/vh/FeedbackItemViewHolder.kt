package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ItemFeedbackBinding
import com.imax.edumeet.presentation.models.FeedbackItem
import com.imax.edumeet.presentation.models.group.GroupItem

class FeedbackItemViewHolder(private val binding: ItemFeedbackBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedbackItem) {
        binding.authorName.text = item.name
        binding.authorComment.text = item.comment
        binding.ratingBar.rating = item.rating

        Glide.with(binding.root.context)
            .load(item.profileImage)
            .circleCrop()
            .placeholder(R.drawable.ic_profile_fill)
            .into(binding.authorImage)

    }
}
