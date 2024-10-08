package com.imax.edumeet.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.login.User
import com.imax.edumeet.data.remote.models.stream.my.MyStreamsResponse
import com.imax.edumeet.data.repository.AuthRepository
import com.imax.edumeet.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val repository: AuthRepository, private val localPreferences: LocalPreferences, private val streamRepository: StreamRepository): ViewModel() {

    private val _userState = MutableStateFlow(NetworkState<User>())
    internal val userState = _userState.asStateFlow()

    fun getUser() = viewModelScope.launch {
        repository.getUser()
            .onStart { _userState.value = NetworkState(isLoading = true) }
            .catch { e ->
                _userState.value = NetworkState(isLoading = false, ResultModel.error(e))
            }
            .collect { r ->
                when (r.status) {
                    Status.SUCCESS -> {
                        r.data?.let {
                            localPreferences.saveUser(it)
                        }

                        _userState.value =
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(r.data)
                            )

                    }

                    Status.ERROR -> _userState.value =
                        NetworkState(
                            isLoading = false,
                            result = r.errorThrowable?.let { ResultModel.error(it) })

                }
            }
    }

    private val _myStreamsState = MutableStateFlow(NetworkState<MyStreamsResponse>())
    internal val myStreamsState = _myStreamsState.asStateFlow()
    fun getMyStreams() = viewModelScope.launch {
        streamRepository.getMyStreams(localPreferences.getUser().id)
            .onStart { _myStreamsState.value = NetworkState(isLoading = true) }
            .catch { e ->
                _myStreamsState.value = NetworkState(isLoading = false, ResultModel.error(e))
            }
            .collect { r ->
                when (r.status) {
                    Status.SUCCESS -> {
                        _myStreamsState.value =
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(r.data)
                            )

                    }

                    Status.ERROR -> _myStreamsState.value =
                        NetworkState(
                            isLoading = false,
                            result = r.errorThrowable?.let { ResultModel.error(it) })

                }
            }
    }


}
