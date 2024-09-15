package com.imax.edumeet.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.imax.edumeet.R
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.databinding.ScreenSplashBinding
import com.imax.edumeet.presentation.screens.onboard.OnBoardScreen
import com.imax.navigation.NavigationExtensions.replaceFragment
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

private const val SPLASH_SCREEN_DURATION = 3000L // 3 seconds

@SuppressLint("CustomSplashScreen")
class SplashScreen: Fragment(R.layout.screen_splash) {
    private val binding by viewBinding<ScreenSplashBinding>()
    private val localPreferences: LocalPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottie.playAnimation()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            if (localPreferences.isLoggedIn()){
                requireActivity().supportFragmentManager.replaceFragment(R.id.activity_main_container, MainScreen())
            } else {
                requireActivity().supportFragmentManager.replaceFragment(R.id.activity_main_container, OnBoardScreen())
            }
        }
    }
}
