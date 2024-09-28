package com.imax.edumeet.data.remote.models

import com.google.gson.annotations.SerializedName
import com.imax.edumeet.data.remote.models.feedback.FeedbackTeacher
import com.imax.edumeet.data.remote.models.stream.Teacher

data class Comment(
    val user: Teacher,
    val comment: String,
    @SerializedName("_id")
    val id: String,
    val date: String
)
