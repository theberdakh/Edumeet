package com.imax.edumeet.data.remote.models.stream.my

import com.google.gson.annotations.SerializedName
import com.imax.edumeet.data.remote.models.feedback.FeedbackTeacher
import com.imax.edumeet.data.remote.models.stream.Teacher
import com.imax.edumeet.presentation.models.FeedbackItem

data class Rating(
    val teacher: Teacher,
    val rate: Float,
    val comment: String,
    val read: Boolean,
    @SerializedName("_id")
    val id: String,
    val date: String
)

fun Rating.toFeedbackItem() = FeedbackItem(
    profileImage = teacher.profileImage,
    name = teacher.name,
    rating = rate,
    comment = comment
)
