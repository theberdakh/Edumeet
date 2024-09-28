package com.imax.edumeet.data.remote.models.stream

import com.imax.edumeet.data.remote.models.stream.my.Rating

data class RatingData(
    val ratings: List<Rating>,
    val totalRating: Float
)
