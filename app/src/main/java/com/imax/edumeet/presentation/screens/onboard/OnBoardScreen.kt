package com.imax.edumeet.presentation.screens.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ScreenOnboardBinding
import com.imax.edumeet.presentation.models.onboard.OnBoardingModel
import com.imax.edumeet.presentation.screens.login.LoginScreen
import com.imax.navigation.NavigationExtensions.replaceFragment
import com.imax.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardScreen: Fragment(R.layout.screen_onboard) {
    private val binding by viewBinding<ScreenOnboardBinding>()
    private val mOnBoardScreenViewModel by viewModel<OnBoardScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val onBoardingModels = mOnBoardScreenViewModel.onBoardingModels
        var onBoardingModelPosition = 0
        setResources(onBoardingModel = onBoardingModels[onBoardingModelPosition])

        binding.btnNext.setOnClickListener {
            if (onBoardingModelPosition >= onBoardingModels.lastIndex){
                requireActivity().supportFragmentManager.replaceFragment(R.id.activity_main_container, LoginScreen())
            } else {
                onBoardingModelPosition +=1
                setResources(onBoardingModel = onBoardingModels[onBoardingModelPosition])
            }

        }

    }

    private fun setResources(onBoardingModel: OnBoardingModel) {
        binding.illustration.setImageResource(onBoardingModel.image)
        binding.progressDot.setImageResource(onBoardingModel.progressImage)
        binding.title.setText(onBoardingModel.title)
        binding.caption.setText(onBoardingModel.caption)
    }
}
