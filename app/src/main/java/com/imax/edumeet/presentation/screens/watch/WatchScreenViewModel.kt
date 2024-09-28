package com.imax.edumeet.presentation.screens.watch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.feedback.FeedbackRequest
import com.imax.edumeet.data.remote.models.feedback.FeedbackTeacher
import com.imax.edumeet.data.remote.models.stream.StreamResponse
import com.imax.edumeet.data.remote.models.stream.my.toFeedbackItem
import com.imax.edumeet.data.remote.models.video.VideoResponse
import com.imax.edumeet.data.repository.StreamRepository
import com.imax.edumeet.presentation.models.FeedbackItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WatchScreenViewModel(private val repository: StreamRepository, private val localPreferences: LocalPreferences): ViewModel() {

    private val _videoState = MutableStateFlow(NetworkState<VideoResponse>())
    internal val videoState = _videoState.asStateFlow()
    fun getVideo(streamId: String) = viewModelScope.launch {
        repository.getVideo(streamId)
            .onStart {
                _videoState.emit(NetworkState(isLoading = true))
            }
            .catch {
                _videoState.emit(NetworkState(isLoading = false, ResultModel.error(it)))
            }
            .collect {
                when(it.status){
                    Status.SUCCESS -> _videoState.emit(NetworkState(isLoading = false, ResultModel.success(it.data)))
                    Status.ERROR -> _videoState.emit(NetworkState(isLoading = false,
                        it.errorThrowable?.let { error -> ResultModel.error(error) }))
                }
            }
    }

    private val _sendFeedbackState = MutableStateFlow(NetworkState<StreamResponse>())
    internal val sendFeedbackState = _sendFeedbackState.asStateFlow()
    fun sendFeedback(streamId: String, feedbackRating: Float, feedbackText: String) = viewModelScope.launch {
        val feedbackTeacher = FeedbackTeacher(localPreferences.getUser().id, localPreferences.getUser().name)
        repository.sendFeedback(streamId, FeedbackRequest(feedbackTeacher, feedbackRating, feedbackText))
            .onStart {
                _sendFeedbackState.emit(NetworkState(isLoading = true))
            }
            .catch {
                _sendFeedbackState.emit(NetworkState(isLoading = false, ResultModel.error(it)))
            }
            .collect {
               _sendFeedbackState.value =  when(it.status){
                    Status.SUCCESS -> NetworkState(isLoading = false, ResultModel.success(it.data))
                    Status.ERROR -> NetworkState(isLoading = false, it.errorThrowable?.let { error -> ResultModel.error(error) })
                }
            }
    }

    private val _feedbacksState = MutableStateFlow(NetworkState<List<FeedbackItem>>())
    internal val feedbacksState = _feedbacksState.asStateFlow()
    fun getFeedbacks(streamId: String) = viewModelScope.launch {
        repository.getFeedbacks(streamId)
            .onStart {
                _feedbacksState.emit(NetworkState(isLoading = true))
            }
            .catch {
                _feedbacksState.emit(NetworkState(isLoading = false, ResultModel.error(it)))
            }
            .collect {
                _feedbacksState.value =  when(it.status){
                    Status.SUCCESS -> {
                        val feedbacks = it.data?.data?.rating?.ratings?.map { filteredRating ->
                            filteredRating.toFeedbackItem()
                        }
                        NetworkState(isLoading = false, ResultModel.success(feedbacks))
                    }
                    Status.ERROR -> NetworkState(isLoading = false, it.errorThrowable?.let { error -> ResultModel.error(error) })
                }
            }
    }

    private val _teacherFeedback = MutableStateFlow(NetworkState<List<FeedbackItem>>())
    internal val teacherFeedback = _teacherFeedback.asStateFlow()
    fun getTeacherFeedbacks(streamId: String) = viewModelScope.launch {
        repository.getFeedbacks(streamId)
            .onStart {
                _teacherFeedback.emit(NetworkState(isLoading = true))
            }
            .catch {
                _teacherFeedback.emit(NetworkState(isLoading = false, ResultModel.error(it)))
            }
            .collect {
              _teacherFeedback.value = when(it.status){
                    Status.SUCCESS -> {
                        Log.i("Teacher Feedback: Success", streamId)
                        Log.i("Teacher Feedback: Success", it.toString())
                        val feedbacks = it.data?.data?.rating?.ratings?.filter { rating ->
                            rating.teacher.id == localPreferences.getUser().id
                        }?.map { filteredRating ->
                            filteredRating.toFeedbackItem()
                        }
                       NetworkState(isLoading = false, ResultModel.success(feedbacks))
                    }
                    Status.ERROR -> NetworkState(isLoading = false, it.errorThrowable?.let { error -> ResultModel.error(error) })
                }
            }
    }



}
