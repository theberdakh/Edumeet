package com.imax.edumeet.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ScreenMainBinding
import com.imax.edumeet.presentation.screens.home.HomeScreen
import com.imax.edumeet.presentation.screens.login.LoginScreen
import com.imax.edumeet.presentation.screens.profile.ProfileScreen
import com.imax.edumeet.presentation.screens.schedule.ScheduleScreen
import com.imax.navigation.NavigationExtensions.addFragmentToBackStack
import com.imax.navigation.NavigationExtensions.replaceFragment
import com.imax.viewbinding.viewBinding
import org.koin.android.ext.android.get
import java.lang.Exception

const val KEY_CURRENT_ROAD = ""
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding<ScreenMainBinding>()
    private val HOME = "home"
    private val PROFILE = "profile"
    private var LATEST_ROUTE = HOME

    override fun onResume() {
        super.onResume()

        if (LATEST_ROUTE == HOME){
            childFragmentManager.replaceFragment(R.id.fragment_main_container, HomeScreen())
            binding.toolbar.title = getString(R.string.main)
        } else {
            childFragmentManager.replaceFragment(R.id.fragment_main_container, ProfileScreen())
            binding.toolbar.title = getString(R.string.profile)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            LATEST_ROUTE = savedInstanceState.getString(KEY_CURRENT_ROAD, HOME)
        }


        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.action_home -> {
                    binding.toolbar.title = getString(R.string.main)
                    LATEST_ROUTE = HOME
                    HomeScreen()
                }
                R.id.action_profile -> {
                    binding.toolbar.title = getString(R.string.profile)
                    LATEST_ROUTE = PROFILE
                    ProfileScreen()
                }
                else -> HomeScreen()
            }
            childFragmentManager.replaceFragment(R.id.fragment_main_container, fragment)
            true
        }
        binding.fabAdd.setOnClickListener {
            requireActivity().supportFragmentManager.addFragmentToBackStack(R.id.activity_main_container, ScheduleScreen.newInstance())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_CURRENT_ROAD, LATEST_ROUTE)
    }
}
