package com.imax.edumeet.presentation.screens.onboard

import androidx.lifecycle.ViewModel
import com.imax.edumeet.presentation.models.onboard.OnBoardingModelRepository

class OnBoardScreenViewModel: ViewModel() {
    val onBoardingModels = OnBoardingModelRepository.getAllOnBoardingModels()
}
