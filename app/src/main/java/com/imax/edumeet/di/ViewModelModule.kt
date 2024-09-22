package com.imax.edumeet.di

import com.imax.edumeet.presentation.screens.home.HomeScreenViewModel
import com.imax.edumeet.presentation.screens.login.LoginViewModel
import com.imax.edumeet.presentation.screens.onboard.OnBoardScreenViewModel
import com.imax.edumeet.presentation.screens.profile.ProfileScreenViewModel
import com.imax.edumeet.presentation.screens.schedule.ScheduleScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<OnBoardScreenViewModel> {
        OnBoardScreenViewModel()
    }

    viewModel<LoginViewModel> {
        LoginViewModel(localPreferences = get(), authRepository = get())
    }

    viewModel<HomeScreenViewModel>{
        HomeScreenViewModel(repository = get())
    }

    viewModel<ScheduleScreenViewModel>{
        ScheduleScreenViewModel(repository = get(), localPreferences = get())
    }

    viewModel <ProfileScreenViewModel>{
        ProfileScreenViewModel(repository = get(), localPreferences = get())
    }

}
