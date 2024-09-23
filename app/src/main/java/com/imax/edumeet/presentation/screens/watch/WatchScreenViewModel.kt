package com.imax.edumeet.presentation.screens.watch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.video.VideoResponse
import com.imax.edumeet.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WatchScreenViewModel(private val repository: StreamRepository): ViewModel() {

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

}