package com.imax.edumeet.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.stream.live.toLiveStreamItem
import com.imax.edumeet.data.remote.models.stream.toLiveStreamItem
import com.imax.edumeet.data.remote.models.stream.toStreamItem
import com.imax.edumeet.data.repository.StreamRepository
import com.imax.edumeet.presentation.models.stream.LiveStreamItem
import com.imax.edumeet.presentation.models.stream.StreamItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: StreamRepository) : ViewModel() {

    private val _allStreamsState = MutableStateFlow(NetworkState<List<StreamItem>>())
    internal val allStreamsState: StateFlow<NetworkState<List<StreamItem>>> =
        _allStreamsState.asStateFlow()

    fun getPreviousStreams() = viewModelScope.launch {
        repository.getPreviousStreams()
            .onStart {_allStreamsState.value = NetworkState(isLoading = true)}
            .catch { _allStreamsState.value = NetworkState(isLoading = false, ResultModel.error(it)) }
            .collect { r ->
            when (r.status) {
                Status.SUCCESS -> {
                    _allStreamsState.value =
                        NetworkState(
                            isLoading = false,
                            result = ResultModel.success(r.data?.map { it.toStreamItem() })
                        )
                }

                Status.ERROR -> _allStreamsState.value =
                    NetworkState(
                        isLoading = false,
                        result = r.errorThrowable?.let { ResultModel.error(it) })

            }
        }
    }

     private val _plannedStreamsState = MutableStateFlow(NetworkState<List<LiveStreamItem>>())
      internal val plannedStreamsState: StateFlow<NetworkState<List<LiveStreamItem>>> = _plannedStreamsState.asStateFlow()
      fun getPlannedStreams() = viewModelScope.launch {
             repository.getPlannedStreams()
                 .onStart {_plannedStreamsState.value = NetworkState(isLoading = true)}
                 .catch { _plannedStreamsState.value = NetworkState(isLoading = false, ResultModel.error(it)) }
                 .collect { r ->
                 when (r.status) {
                     Status.SUCCESS -> {
                         _plannedStreamsState.value =
                             NetworkState(
                                 isLoading = false,
                                 result = ResultModel.success(r.data?.map { it.toLiveStreamItem() })
                             )
                     }

                     Status.ERROR -> _plannedStreamsState.value =
                         NetworkState(
                             isLoading = false,
                             result = r.errorThrowable?.let { ResultModel.error(it) })

                 }
             }
         }

    private val _liveStreamsState = MutableStateFlow(NetworkState<List<LiveStreamItem>>())
    internal val liveStreamsState: StateFlow<NetworkState<List<LiveStreamItem>>> = _liveStreamsState.asStateFlow()
    fun getLiveStreams() = viewModelScope.launch {
        repository.getLiveStreams()
            .onStart {_liveStreamsState.value = NetworkState(isLoading = true)}
            .catch { _liveStreamsState.value = NetworkState(isLoading = false, ResultModel.error(it)) }
            .collect { r ->
                when (r.status) {
                    Status.SUCCESS -> {
                        _liveStreamsState.value =
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(r.data?.map { it.toLiveStreamItem() })
                            )
                    }

                    Status.ERROR -> _liveStreamsState.value =
                        NetworkState(
                            isLoading = false,
                            result = r.errorThrowable?.let { ResultModel.error(it) })

                }
            }
    }


}
