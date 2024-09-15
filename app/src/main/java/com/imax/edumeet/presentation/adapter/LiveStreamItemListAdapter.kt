package com.imax.edumeet.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.imax.edumeet.databinding.ItemLiveStreamBinding
import com.imax.edumeet.presentation.adapter.vh.LiveStreamItemViewHolder
import com.imax.edumeet.presentation.models.stream.LiveStreamItem
import com.imax.edumeet.presentation.models.stream.LiveStreamItemCallback

class LiveStreamItemListAdapter :
    ListAdapter<LiveStreamItem, LiveStreamItemViewHolder>(LiveStreamItemCallback) {

    private var onLiveStreamItemClickListener: ((LiveStreamItem) -> Unit)? = null
    fun setOnLiveStreamItemClickListener(block: ((LiveStreamItem) -> Unit)) {
        onLiveStreamItemClickListener= block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveStreamItemViewHolder {
        return LiveStreamItemViewHolder(
            ItemLiveStreamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)


    }

    override fun onBindViewHolder(holder: LiveStreamItemViewHolder, position: Int) =
        holder.bind(getItem(position), onLiveStreamItemClickListener)

}
