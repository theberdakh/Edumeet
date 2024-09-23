package com.imax.edumeet.data.repository

import com.imax.edumeet.data.remote.api.EduMeetApi
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.stream.StreamRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class StreamRepository(private val api: EduMeetApi) {

    fun getPreviousStreams() = flow {
        val response = api.getPreviousStreams()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getPlannedStreams() = flow {
        val response = api.getPlannedStreams()
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

    fun getLiveStreams() = flow {
        val response = api.getLiveStreams()
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getVideo(streamId: String) = flow {
        val response = api.getVideo(streamId)
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

    fun getMyStreams(teacherId: String) = flow {
        val response = api.getMyStreams(teacherId)
        emit(ResultModel.success(response))
    }.catch {
        emit(ResultModel.error(it))
    }

}
