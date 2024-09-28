package com.imax.edumeet.presentation.models.stream

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class LiveStreamItem(
    val id: String = "",
    val streamTitle: String = "",
    val streamStatus: String = "",
    val streamDate: String = "",
    val streamGroup: String = "",
    val playerUrl: String = "",
    val authorName: String = "",
    val authorSubject: String = "",
    val authorProfile: String = "",
): Parcelable

object LiveStreamItemCallback: DiffUtil.ItemCallback<LiveStreamItem>() {
    override fun areItemsTheSame(oldItem: LiveStreamItem, newItem: LiveStreamItem): Boolean {
        return oldItem.streamTitle == newItem.streamTitle && oldItem.streamDate == newItem.streamDate
    }

    override fun areContentsTheSame(oldItem: LiveStreamItem, newItem: LiveStreamItem): Boolean {
        return oldItem == newItem
    }
}

