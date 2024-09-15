package com.imax.edumeet.data.repository

import com.imax.edumeet.data.remote.api.EduMeetApi
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.stream.StreamRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class StreamRepository(private val api: EduMeetApi) {

    fun getAllStreams() = flow {
        val response = api.getAllStreams()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getLiveStreams() = flow {
        val response = api.getLiveStreams()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getGroups() = flow {
        val response = api.getGroups()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun createStream(streamRequest: StreamRequest) = flow {
        val response = api.createStream(streamRequest)
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }
}
