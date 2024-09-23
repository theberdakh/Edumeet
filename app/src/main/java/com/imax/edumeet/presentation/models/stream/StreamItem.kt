package com.imax.edumeet.presentation.models.stream

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class StreamItem(
    val streamTitle: String = "",
    val streamStatus: String = "",
    val streamDate: String = "",
    val streamGroup: String = "",
    val streamUrl: String = "",
    val authorName: String = "",
    val authorSubject: String = "",
    val authorProfile: String = "",
    val streamId: String = ""
): Parcelable

object StreamItemCallback: DiffUtil.ItemCallback<StreamItem>() {
    override fun areItemsTheSame(oldItem: StreamItem, newItem: StreamItem): Boolean {
        return oldItem.streamTitle == newItem.streamTitle && oldItem.streamDate == newItem.streamDate && oldItem.streamId == newItem.streamId
    }

    override fun areContentsTheSame(oldItem: StreamItem, newItem: StreamItem): Boolean {
        return oldItem == newItem
    }
}
