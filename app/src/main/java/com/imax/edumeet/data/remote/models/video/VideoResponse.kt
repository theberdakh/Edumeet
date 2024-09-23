package com.imax.edumeet.data.remote.models.video

data class VideoResponse(
    val iframe: String,
    val player: String,
    val hls: String,
    val thumbnail: String,
    val mp4: String
)
