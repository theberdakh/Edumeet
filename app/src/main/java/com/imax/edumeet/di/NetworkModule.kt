package com.imax.edumeet.di

import android.util.Log
import com.imax.edumeet.BuildConfig
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.api.EduMeetApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { provideRetrofit(get()) }
    single<EduMeetApi>{
        get<Retrofit>().create(EduMeetApi::class.java)
    }
}

fun provideRetrofit(localPreferences: LocalPreferences): Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    val tokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = localPreferences.getToken()

        val newRequest = if (token.isNotEmpty()) {
            Log.i("Token", "$token")
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        chain.proceed(newRequest)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(tokenInterceptor)
        .build()

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .build()
}


