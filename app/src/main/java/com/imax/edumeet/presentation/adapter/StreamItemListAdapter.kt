package com.imax.edumeet.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.imax.edumeet.databinding.ItemStreamBinding
import com.imax.edumeet.presentation.adapter.vh.StreamItemViewHolder
import com.imax.edumeet.presentation.models.stream.StreamItem
import com.imax.edumeet.presentation.models.stream.StreamItemCallback

class StreamItemListAdapter: ListAdapter<StreamItem, StreamItemViewHolder>(StreamItemCallback) {

    private var onStreamItemClickListener: ((StreamItem) -> Unit)? = null
    fun setOnStreamItemClickListener(block: ((StreamItem) -> Unit)) {
        onStreamItemClickListener= block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamItemViewHolder {
        return StreamItemViewHolder(ItemStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StreamItemViewHolder, position: Int) {
        holder.bind(getItem(position), onStreamItemClickListener)
    }
}
