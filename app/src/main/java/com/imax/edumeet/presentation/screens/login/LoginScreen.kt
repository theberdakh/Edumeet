package com.imax.edumeet.presentation.screens.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.imax.ToastHelper
import com.imax.edumeet.R
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.errorMessage
import com.imax.edumeet.databinding.ScreenLoginBinding
import com.imax.edumeet.presentation.screens.MainScreen
import com.imax.extensions.ViewExtensions.gone
import com.imax.extensions.ViewExtensions.trimmedString
import com.imax.extensions.ViewExtensions.visible
import com.imax.navigation.NavigationExtensions.replaceFragment
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreen : Fragment(R.layout.screen_login) {
    private val binding by viewBinding<ScreenLoginBinding>()
    private val loginViewModel by viewModel<LoginViewModel>()
    private val toastHelper by inject<ToastHelper>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginViewModel()

        binding.btnEnter.setOnClickListener {
            val username = binding.etUsername.trimmedString
            val password = binding.etPassword.trimmedString
            loginViewModel.login(username, password)
        }

    }

    private fun observeLoginViewModel() {
        loginViewModel.loginState.onEach {
            if (it.isLoading) {
                binding.btnEnterText.gone()
                binding.loginProgress.visible()
            } else {
                when (it.result?.status) {
                    Status.SUCCESS -> requireActivity().supportFragmentManager.replaceFragment(
                        R.id.activity_main_container,
                        MainScreen()
                    )

                    Status.ERROR -> it.result.errorThrowable?.errorMessage?.let { errorMessage ->
                        toastHelper.showToast(errorMessage)
                    }

                    null -> toastHelper.showToast("null")
                }
                binding.btnEnterText.visible()
                binding.loginProgress.gone()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
