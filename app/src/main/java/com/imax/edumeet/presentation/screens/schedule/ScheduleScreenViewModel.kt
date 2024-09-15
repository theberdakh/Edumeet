package com.imax.edumeet.presentation.screens.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.group.toGroupItem
import com.imax.edumeet.data.remote.models.login.User
import com.imax.edumeet.data.remote.models.stream.StreamRequest
import com.imax.edumeet.data.remote.models.stream.StreamRequestTeacher
import com.imax.edumeet.data.remote.models.stream.StreamResponse
import com.imax.edumeet.data.repository.StreamRepository
import com.imax.edumeet.presentation.models.group.GroupItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ScheduleScreenViewModel(
    private val repository: StreamRepository,
    private val localPreferences: LocalPreferences
) : ViewModel() {

    private val _groupsState = MutableStateFlow(NetworkState<List<GroupItem>>())
    internal val groupState = _groupsState.asStateFlow()
    fun getAllGroups() = viewModelScope.launch {
        repository.getGroups()
            .onStart { _groupsState.emit(NetworkState(isLoading = true)) }
            .catch {
                _groupsState.emit(
                    NetworkState(
                        isLoading = false,
                        result = ResultModel.error(it)
                    )
                )
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        val groupItems = it.data?.map { groupResponse ->
                            groupResponse.toGroupItem()
                        }
                        _groupsState.emit(
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(groupItems)
                            )
                        )
                    }

                    Status.ERROR -> it.errorThrowable?.let { e ->
                        _groupsState.emit(
                            NetworkState(
                                isLoading = false,
                                ResultModel.error(e)
                            )
                        )
                    }
                }
            }
    }

    private val _userState = MutableStateFlow(NetworkState<User>())
    internal val userState = _userState.asStateFlow()
    fun getUser() = viewModelScope.launch {
        Log.d("Schedule", "${localPreferences.getUser()}")
        _userState.emit(
            NetworkState(
                isLoading = false,
                result = ResultModel.success(localPreferences.getUser())
            )
        )
    }

    private val _streamState = MutableStateFlow(NetworkState<StreamResponse>())
    internal val streamState = _streamState.asStateFlow()
    fun createStream(
        title: String,
        group: String,
        science: String,
        teacherId: String,
        teacherName: String,
        teacherProfileImage: String
    ) = viewModelScope.launch {

        val teacher = StreamRequestTeacher(teacherId, teacherName, teacherProfileImage)
        val streamRequest = StreamRequest(
            title = title,
            group = group,
            science = science,
            teacher = teacher,
            classRoom = "201"
        )

        repository.createStream(streamRequest)
            .onStart { _streamState.emit(NetworkState(isLoading = true)) }
            .catch {
                _streamState.emit(
                    NetworkState(
                        isLoading = false,
                        result = ResultModel.error(it)
                    )
                )
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _streamState.emit(
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(it.data)
                            )
                        )
                    }

                    Status.ERROR -> it.errorThrowable?.let { e ->
                        _streamState.emit(
                            NetworkState(
                                isLoading = false,
                                ResultModel.error(e)
                            )
                        )
                    }
                }
            }
    }


}
