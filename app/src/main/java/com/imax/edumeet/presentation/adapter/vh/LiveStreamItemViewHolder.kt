package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.imax.edumeet.databinding.ItemLiveStreamBinding
import com.imax.edumeet.presentation.models.stream.LiveStreamItem

class LiveStreamItemViewHolder(private val binding: ItemLiveStreamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LiveStreamItem, onStreamItemClickListener: ((LiveStreamItem) -> Unit)?) {
        binding.streamTitle.text = item.streamTitle
        binding.authorName.text = item.authorName
        binding.authorSubject.text = item.authorSubject
        binding.streamGroup.text = item.streamGroup

        binding.root.setOnClickListener {
            onStreamItemClickListener?.invoke(item)
        }
    }
}
