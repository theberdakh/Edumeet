package com.imax.edumeet.data.remote.models.stream.live

import com.google.gson.annotations.SerializedName
import com.imax.edumeet.presentation.models.stream.LiveStreamItem

data class LiveStreamResponse(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val classRoom: String,
    val comments: List<Any>,
    val createdAt: String,
    val description: String,
    val group: String,
    val isEnded: Boolean,
    val isStart: Boolean,
    val planStream: String?,
    val rating: LiveStreamRating,
    val science: String,
    val streamId: String,
    val streamInfo: LiveStreamInfo,
    val teacher: LiveStreamTeacher,
    val title: String,
    val updatedAt: String,
    val viewers: List<Any>
)

fun LiveStreamResponse.toLiveStreamItem(): LiveStreamItem {
    return LiveStreamItem(
        streamTitle = title,
        streamDate = streamInfo.updatedAt,
        streamGroup = group,
        playerUrl = streamInfo.assets.iframe,
        authorName = teacher.name,
        authorSubject = science,
        authorProfile = teacher.profileImage
    )
}
