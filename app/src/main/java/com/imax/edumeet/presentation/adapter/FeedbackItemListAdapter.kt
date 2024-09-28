package com.imax.edumeet.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.imax.edumeet.databinding.ItemFeedbackBinding
import com.imax.edumeet.presentation.adapter.vh.FeedbackItemViewHolder
import com.imax.edumeet.presentation.models.FeedbackItem
import com.imax.edumeet.presentation.models.FeedbackItemDiffCallback

class FeedbackItemListAdapter: ListAdapter<FeedbackItem, FeedbackItemViewHolder>(FeedbackItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackItemViewHolder {
        return FeedbackItemViewHolder(ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedbackItemViewHolder, position: Int) = holder.bind(getItem(position))

}
