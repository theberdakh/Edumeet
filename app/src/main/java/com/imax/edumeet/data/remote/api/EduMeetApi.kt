package com.imax.edumeet.data.remote.api

import com.imax.edumeet.data.remote.models.feedback.FeedbackRequest
import com.imax.edumeet.data.remote.models.feedback.FeedbackResponse
import com.imax.edumeet.data.remote.models.group.GroupResponse
import com.imax.edumeet.data.remote.models.login.LoginRequest
import com.imax.edumeet.data.remote.models.login.LoginResponse
import com.imax.edumeet.data.remote.models.login.User
import com.imax.edumeet.data.remote.models.stream.StreamRequest
import com.imax.edumeet.data.remote.models.stream.StreamResponse
import com.imax.edumeet.data.remote.models.stream.live.LiveStreamResponse
import com.imax.edumeet.data.remote.models.stream.my.MyStreamsResponse
import com.imax.edumeet.data.remote.models.video.VideoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EduMeetApi {

    @POST("/login-teacher")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/teacher/me")
    suspend fun getUser(): User

    @GET("/streams/previous")
    suspend fun getPreviousStreams(): List<StreamResponse>

    @GET("/streams/soon")
    suspend fun getPlannedStreams(): List<StreamResponse>

    @GET("/get-groups")
    suspend fun getGroups(): List<GroupResponse>

    @POST("/create-stream")
    suspend fun createStream(@Body streamRequest: StreamRequest): StreamResponse
    
    @GET("/streams/live")
    suspend fun getLiveStreams(): List<LiveStreamResponse>

    @GET("/stream/{streamId}")
    suspend fun getVideo(@Path("streamId") streamId: String): VideoResponse

    @GET("/my-streams/{teacherId}")
    suspend fun getMyStreams(@Path("teacherId") teacherId: String): MyStreamsResponse

    @POST("/stream/{streamId}/feedback")
    suspend fun sendFeedback(@Path("streamId") streamId: String, @Body feedback: FeedbackRequest): StreamResponse

    @GET("/stream/{streamId}/feedbacks")
    suspend fun getFeedbacks(@Path("streamId") streamId: String): FeedbackResponse

}
