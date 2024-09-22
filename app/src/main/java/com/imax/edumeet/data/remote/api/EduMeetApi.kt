package com.imax.edumeet.data.remote.api

import com.imax.edumeet.data.remote.models.group.GroupResponse
import com.imax.edumeet.data.remote.models.login.LoginRequest
import com.imax.edumeet.data.remote.models.login.LoginResponse
import com.imax.edumeet.data.remote.models.login.User
import com.imax.edumeet.data.remote.models.stream.StreamRequest
import com.imax.edumeet.data.remote.models.stream.StreamResponse
import com.imax.edumeet.data.remote.models.stream.live.LiveStreamResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EduMeetApi {

    @POST("/login-teacher")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/teacher/me")
    suspend fun getUser(): User

    @GET("/streams/all")
    suspend fun getAllStreams(): List<StreamResponse>

    @GET("/streams/soon")
    suspend fun getPlannedStreams(): List<StreamResponse>

    @GET("/get-groups")
    suspend fun getGroups(): List<GroupResponse>

    @POST("/create-stream")
    suspend fun createStream(@Body streamRequest: StreamRequest): StreamResponse
    
    @GET("/streams/live")
    suspend fun getLiveStreams(): List<LiveStreamResponse>


}
