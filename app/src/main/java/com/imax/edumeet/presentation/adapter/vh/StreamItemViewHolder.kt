package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.imax.edumeet.databinding.ItemStreamBinding
import com.imax.edumeet.presentation.models.stream.StreamItem

class StreamItemViewHolder(private val binding: ItemStreamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StreamItem, onStreamItemClickListener: ((StreamItem) -> Unit)?){
        binding.authorName.text =item.streamTitle
        binding.authorSubject.text = item.authorName

        binding.root.setOnClickListener {
            onStreamItemClickListener?.invoke(item)
        }
    }
}
