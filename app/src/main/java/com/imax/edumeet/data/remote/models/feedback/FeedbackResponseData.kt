package com.imax.edumeet.data.remote.models.feedback

import com.imax.edumeet.data.remote.models.stream.my.Rating
import org.w3c.dom.Comment


data class FeedbackResponseData(
    val rating: RatingData,
    val comment: List<Comment>
)

data class RatingData(
    val totalRating: Float,
    val ratings: List<Rating>
)
