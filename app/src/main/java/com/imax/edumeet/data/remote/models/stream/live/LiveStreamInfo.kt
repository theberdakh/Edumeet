package com.imax.edumeet.data.remote.models.stream.live

import com.google.gson.annotations.SerializedName

data class LiveStreamInfo(
    val assets: LiveStreamAssets,
    val broadcasting: Boolean,
    val createdAt: String,
    val liveStreamId: String,
    val name: String,
    @SerializedName("public")
    val isPublic: Boolean,
    val record: Boolean,
    @SerializedName("restreams")
    val reStreams: List<Any>,
    val streamKey: String,
    val updatedAt: String
)
