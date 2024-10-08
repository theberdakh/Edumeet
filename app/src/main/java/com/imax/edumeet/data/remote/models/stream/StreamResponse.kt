package com.imax.edumeet.data.remote.models.stream

import com.google.gson.annotations.SerializedName
import com.imax.edumeet.data.remote.models.Comment
import com.imax.edumeet.presentation.models.FeedbackItem
import com.imax.edumeet.presentation.models.stream.LiveStreamItem
import com.imax.edumeet.presentation.models.stream.StreamItem

data class StreamResponse(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val classRoom: String,
    val comments: List<Comment>,
    val description: String,
    val group: String,
    val isEnded: Boolean,
    val planStream: String,
    val rating: RatingData,
    val science: String,
    val streamId: String,
    val streamInfo: StreamInfo,
    val teacher: Teacher,
    val title: String,
    val viewers: List<Any>
)

fun StreamResponse.toLiveStreamItem() = LiveStreamItem(
    id = this.id,
    streamTitle = this.title,
    streamGroup = this.group,
    streamDate = this.streamInfo.createdAt,
    streamStatus = this.streamInfo.broadcasting.toString(),
    playerUrl = this.streamInfo.assets.player,
    authorName = this.teacher.name,
    authorSubject = this.science
)

fun StreamResponse.toStreamItem() = StreamItem(
    id = this.id,
    streamTitle = this.title,
    streamGroup = this.group,
    streamDate = this.streamInfo.createdAt,
    streamStatus = this.streamInfo.broadcasting.toString(),
    streamUrl = this.streamInfo.assets.iframe,
    authorName = this.teacher.name,
    authorSubject = this.science,
    authorProfile = this.teacher.profileImage,
    streamId = this.streamId
)



