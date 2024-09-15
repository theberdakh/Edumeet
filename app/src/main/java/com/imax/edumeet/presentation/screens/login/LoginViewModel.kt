package com.imax.edumeet.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.models.NetworkState
import com.imax.edumeet.data.remote.models.ResultModel
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.login.LoginResponse
import com.imax.edumeet.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val localPreferences: LocalPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(NetworkState<LoginResponse>())
    internal val loginState: StateFlow<NetworkState<LoginResponse>> = _loginState.asStateFlow()
    fun login(userName: String, password: String) = viewModelScope.launch {
        authRepository.login(userName, password)
            .onStart { _loginState.value = NetworkState(isLoading = true) }
            .catch { e ->
                _loginState.value = NetworkState(isLoading = false, ResultModel.error(e))
            }
            .collect { r ->
                when (r.status) {
                    Status.SUCCESS -> {
                        _loginState.value =
                            NetworkState(
                                isLoading = false,
                                result = ResultModel.success(r.data)
                            )
                        r.data?.let {
                            localPreferences.saveUser(it.user)
                            localPreferences.saveToken(it.token)
                            localPreferences.isLoggedIn(true)
                        }
                    }

                    Status.ERROR -> _loginState.value =
                        NetworkState(
                            isLoading = false,
                            result = r.errorThrowable?.let { ResultModel.error(it) })

                }
            }
    }

}
