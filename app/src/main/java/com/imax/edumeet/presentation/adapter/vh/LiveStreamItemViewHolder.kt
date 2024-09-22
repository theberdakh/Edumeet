package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ItemLiveStreamBinding
import com.imax.edumeet.presentation.models.stream.LiveStreamItem

class LiveStreamItemViewHolder(private val binding: ItemLiveStreamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LiveStreamItem, onStreamItemClickListener: ((LiveStreamItem) -> Unit)?) {
        binding.streamTitle.text = item.streamTitle
        binding.authorName.text = item.authorName
        binding.authorSubject.text = item.authorSubject
        binding.streamGroup.text = item.streamGroup

        Glide.with(binding.root.context)
            .load(item.authorProfile)
            .circleCrop()
            .placeholder(R.drawable.ic_profile_fill)
            .into(binding.authorImage)

        binding.root.setOnClickListener {
            onStreamItemClickListener?.invoke(item)
        }
    }
}
