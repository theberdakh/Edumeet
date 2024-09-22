package com.imax.edumeet.data.remote.models.stream.live

data class LiveStreamAssets(
    val hls: String,
    val iframe: String,
    val player: String,
    val thumbnail: String
)
