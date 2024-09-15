package com.imax.edumeet.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EduMeetApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EduMeetApp)
            modules(preferenceModule, networkModule, repositoryModule, viewModelModule)
        }

    }
}
