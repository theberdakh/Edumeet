package com.imax.edumeet.data.remote.models

data class NetworkState<T>(
    val isLoading: Boolean = false,
    val result: ResultModel<T>? = null
)
