package com.imax.edumeet.data.local

import android.content.SharedPreferences
import com.imax.edumeet.data.remote.models.login.User
import com.imax.kepket.data.local.BooleanPreference
import com.imax.kepket.data.local.IntPreference
import com.imax.kepket.data.local.StringPreference

class LocalPreferences(private val pref: SharedPreferences) {

    //Bearer Access Token
    private var tokenPreference by StringPreference(pref)

    //User
    private var userIdPreference by StringPreference(pref)
    private var userNamePreference by StringPreference(pref)
    private var userProfileImagePreference by StringPreference(pref)
    private var userPasswordPreference by StringPreference(pref)
    private var userOriginalPasswordPreference by StringPreference(pref)
    private var userSciencePreference by StringPreference(pref)
    private var userVPreference by IntPreference(pref)

    //other preferences
    private var isLoggedInPreference by BooleanPreference(pref)

    fun isLoggedIn() = isLoggedInPreference
    fun isLoggedIn(isLoggedIn: Boolean) {
        isLoggedInPreference = isLoggedIn
    }

    fun saveUser(user: User) {
        userIdPreference = user.id
        userNamePreference = user.name
        userProfileImagePreference = user.profileImage
        userPasswordPreference = user.password
        userOriginalPasswordPreference = user.originalPassword
        userSciencePreference = user.science
        userVPreference = user.v
    }

    fun getUser() = User(
        id = userIdPreference,
        name = userNamePreference,
        profileImage = userProfileImagePreference,
        password = userPasswordPreference,
        originalPassword = userOriginalPasswordPreference,
        science = userSciencePreference,
        v = userVPreference
    )

    fun saveToken(token: String) {
        tokenPreference = token
    }

    fun getToken() = tokenPreference

}
