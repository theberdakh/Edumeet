package com.imax.edumeet.data.repository

import com.imax.edumeet.data.remote.api.EduMeetApi
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.login.LoginRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepository(private val api: EduMeetApi) {

    fun login(userName: String, password: String) = flow {
        val response = api.login(LoginRequest(name = userName, password = password))
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getUser() = flow {
        val response = api.getUser()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }
}
