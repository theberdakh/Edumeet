package com.imax.edumeet.presentation.adapter.vh

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ItemStreamBinding
import com.imax.edumeet.presentation.models.stream.StreamItem

class StreamItemViewHolder(private val binding: ItemStreamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StreamItem, onStreamItemClickListener: ((StreamItem) -> Unit)?){
        binding.authorName.text =item.streamTitle
        binding.authorSubject.text = item.authorName
        Glide.with(binding.root.context)
            .load(R.drawable.ic_profile_fill)
            .into(binding.authorImage)

        binding.root.setOnClickListener {
            onStreamItemClickListener?.invoke(item)
        }
    }
}
