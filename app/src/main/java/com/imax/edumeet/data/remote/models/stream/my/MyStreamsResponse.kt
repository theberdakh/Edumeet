package com.imax.edumeet.data.remote.models.stream.my

import com.imax.edumeet.data.remote.models.stream.StreamResponse

data class MyStreamsResponse(
    val soon: List<StreamResponse>,
    val live: List<StreamResponse>,
    val previous: List<StreamResponse>
)