package com.imax.edumeet.presentation.models.onboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingModel(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @DrawableRes val progressImage: Int,
    @StringRes val caption: Int
)
