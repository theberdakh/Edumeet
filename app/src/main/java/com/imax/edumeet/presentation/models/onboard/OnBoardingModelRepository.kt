package com.imax.edumeet.presentation.models.onboard

import com.imax.edumeet.R

object OnBoardingModelRepository {
    fun getAllOnBoardingModels() = listOf(
        OnBoardingModel(
            image = R.drawable.illustration_1,
            title = R.string.onboard_title_1,
            progressImage = R.drawable.dot_1,
            caption = R.string.onboard_caption_1
        ),
        OnBoardingModel(
            image = R.drawable.illustration_2,
            title = R.string.onboard_title_2,
            progressImage = R.drawable.dot_2,
            caption = R.string.onboard_caption_3
        ),
        OnBoardingModel(
            image = R.drawable.illustration_3,
            title = R.string.onboard_title_3,
            progressImage = R.drawable.dot_3,
            caption = R.string.onboard_caption_3
        )
    )
}
