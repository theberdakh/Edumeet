package com.imax.edumeet.data.remote.models.feedback

data class FeedbackRequest(
    val teacher: FeedbackTeacher,
    val rate: Float,
    val feedback: String
)
