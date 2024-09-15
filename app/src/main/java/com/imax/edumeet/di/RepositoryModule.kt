package com.imax.edumeet.di

import com.imax.edumeet.data.repository.AuthRepository
import com.imax.edumeet.data.repository.StreamRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        AuthRepository(api = get())
    }
    single {
        StreamRepository(api = get())
    }
}
