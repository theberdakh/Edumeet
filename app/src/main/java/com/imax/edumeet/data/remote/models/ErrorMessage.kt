package com.imax.edumeet.data.remote.models

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

val Throwable.errorMessage get() =
    when(this) {
    is HttpException -> {
        val errorBody = this.response()?.errorBody()?.string()
        errorBody ?: "Unknown error occurred"
    }
    is UnknownHostException -> "Unable to connect to the server. Please check your internet connection."
    is IOException -> "Check your internet connection."
    else -> "Unexpected error: ${this.localizedMessage}"
}
