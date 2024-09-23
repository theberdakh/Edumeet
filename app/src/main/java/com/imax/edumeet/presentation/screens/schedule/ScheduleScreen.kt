package com.imax.edumeet.presentation.screens.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.imax.toast.ToastHelper
import com.imax.edumeet.R
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.errorMessage
import com.imax.edumeet.data.remote.models.login.User
import com.imax.edumeet.databinding.ScreenScheduleBinding
import com.imax.edumeet.presentation.adapter.GroupItemListAdapter
import com.imax.edumeet.presentation.screens.stream.StreamScreen
import com.imax.extensions.ViewExtensions.string
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleScreen: Fragment(R.layout.screen_schedule) {
    private val scheduleScreenViewModel by viewModel<ScheduleScreenViewModel>()
    private val binding by viewBinding<ScreenScheduleBinding>()
    private val groupItemListAdapter by lazy { GroupItemListAdapter() }
    private val toastHelper by inject<ToastHelper>()
    private var teacher: User? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGroups.adapter = groupItemListAdapter
        groupItemListAdapter.onGroupItemClickListener { groupItem ->
            binding.streamGroup.text = groupItem.name
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.streamTitle.setText(R.string.theme)
        binding.etTheme.doAfterTextChanged { text ->
            if (text.toString().isEmpty()){
                binding.streamTitle.setText(R.string.theme)
            } else {
                binding.streamTitle.text = text
            }
        }

        binding.btnStartStreaming.setOnClickListener {
            teacher?.let {
                scheduleScreenViewModel.createStream(
                    title = binding.etTheme.string,
                    group = binding.streamGroup.text.toString(),
                    science = binding.authorSubject.text.toString(),
                    teacherId = it.id,
                    teacherName = it.name,
                    teacherProfileImage = it.profileImage
                )
            }

        }

        observeScreenViewModel()

    }

    private fun observeScreenViewModel() {

        scheduleScreenViewModel.getAllGroups()
        scheduleScreenViewModel.groupState.onEach {
            if(it.isLoading) {
               Log.d("Loading", "Loading")
            } else {
            when (it.result?.status) {
                Status.SUCCESS -> {
                    groupItemListAdapter.submitList(it.result.data)
                }

                Status.ERROR -> it.result.errorThrowable?.errorMessage?.let { errorMessage ->
                    toastHelper.showToast(errorMessage)
                }

                null -> toastHelper.showToast("null")
            }
        }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        scheduleScreenViewModel.getUser()
        scheduleScreenViewModel.userState.onEach {
            if (!it.isLoading){
                when(it.result?.status){
                    Status.SUCCESS -> {
                        it.result.data?.let { user ->
                            teacher = user
                            binding.authorName.text = user.name
                            binding.authorSubject.text = user.science
                            Glide.with(requireContext())
                                .load(user.profileImage)
                                .circleCrop()
                                .placeholder(R.drawable.ic_profile_fill)
                                .into(binding.authorImage)
                        }

                    }
                    Status.ERROR -> toastHelper.showToast("Error retrieving user data")
                    null -> toastHelper.showToast("User is null")
                }
            }
            Log.d("Schedule state", "${it}")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        scheduleScreenViewModel.streamState.onEach {
            if (!it.isLoading){
                if (it.result != null){
                    when(it.result.status){
                        Status.SUCCESS -> {
                            it.result.data?.let { streamResponse ->
                                toastHelper.showToast("Stream created successfully!")
                                requireActivity().supportFragmentManager.popBackStack()
                                val intent = Intent(requireActivity(), StreamScreen::class.java)
                                val bundle = Bundle().apply {
                                    putString("STREAM_KEY", streamResponse.streamInfo.streamKey)
                                }
                                intent.putExtras(bundle)
                                startActivity(intent)

                            }

                        }
                        Status.ERROR -> toastHelper.showToast("Error retrieving user data")
                    }
                }

            }
            Log.d("Schedule state", "${it}")
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        fun newInstance(): ScheduleScreen {
            return ScheduleScreen()
        }
    }

}
