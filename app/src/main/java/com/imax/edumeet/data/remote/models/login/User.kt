package com.imax.edumeet.data.remote.models.login

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val profileImage: String,
    val password: String,
    val originalPassword: String,
    val science: String,
    @SerializedName("__v")
    val v: Int
)
