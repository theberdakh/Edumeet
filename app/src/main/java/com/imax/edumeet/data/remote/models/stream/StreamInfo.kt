package com.imax.edumeet.data.remote.models.stream

import com.google.gson.annotations.SerializedName

data class StreamInfo(
    val assets: Assets,
    val broadcasting: Boolean,
    val createdAt: String,
    val liveStreamId: String,
    val name: String,
    @SerializedName("public")
    val isPublic: Boolean,
    val record: Boolean,
    val restreams: List<Any>,
    val streamKey: String,
    val updatedAt: String
)
