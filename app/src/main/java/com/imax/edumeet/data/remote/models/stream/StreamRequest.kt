package com.imax.edumeet.data.remote.models.stream

data class StreamRequest(
    val title: String,
    val description: String = "",
    val planStream: String = "",
    val classRoom: String ="",
    val group: String,
    val science: String,
    val teacher: StreamRequestTeacher
)

data class StreamRequestTeacher(
    val id: String,
    val name: String,
    val profileImage: String
)
