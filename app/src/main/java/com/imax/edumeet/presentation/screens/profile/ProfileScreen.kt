package com.imax.edumeet.presentation.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.imax.toast.ToastHelper
import com.imax.edumeet.R
import com.imax.edumeet.data.local.LocalPreferences
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.errorMessage
import com.imax.edumeet.data.remote.models.stream.toStreamItem
import com.imax.edumeet.databinding.ScreenProfileBinding
import com.imax.edumeet.presentation.adapter.LiveStreamItemListAdapter
import com.imax.edumeet.presentation.adapter.StreamItemListAdapter
import com.imax.edumeet.presentation.screens.watch.WatchLiveScreen
import com.imax.edumeet.presentation.screens.watch.WatchVODScreen
import com.imax.navigation.NavigationExtensions.addFragmentToBackStack
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileScreen: Fragment(R.layout.screen_profile) {
    private val binding by viewBinding<ScreenProfileBinding>()
    private val liveStreamItemListAdapter by lazy { LiveStreamItemListAdapter() }
    private val streamItemListAdapter by lazy { StreamItemListAdapter() }
    private val localPreferences by inject<LocalPreferences>()
    private val toastHelper by inject<ToastHelper>()
    private val profileScreenViewModel by viewModel<ProfileScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeHomeScreenViewModel()

        binding.rvLiveStreams.adapter = liveStreamItemListAdapter
        binding.rvPlannedStreams.adapter = streamItemListAdapter

        binding.swipeRefresh.setOnRefreshListener {
            observeHomeScreenViewModel()
        }

        liveStreamItemListAdapter.setOnLiveStreamItemClickListener { liveStreamItem ->
            requireActivity().supportFragmentManager.addFragmentToBackStack(R.id.activity_main_container, WatchLiveScreen.newInstance(liveStreamItem) )
        }
        streamItemListAdapter.setOnStreamItemClickListener { streamItem ->
            requireActivity().supportFragmentManager.addFragmentToBackStack(R.id.activity_main_container, WatchVODScreen.newInstance(streamItem) )
        }









    }

    private fun loadUserPreferences() {
        localPreferences.getUser().apply {
            Log.i("ProfileScreen", profileImage)
            Glide.with(requireContext())
                .load(profileImage)
                .circleCrop()
                .placeholder(R.drawable.ic_profile_fill)
                .into(binding.image)


            binding.name.text = name
            binding.subject.text = science
        }
    }

    private fun observeHomeScreenViewModel() {

        profileScreenViewModel.getUser()
        profileScreenViewModel.userState.onEach {
            if (it.isLoading) {
                binding.swipeRefresh.isRefreshing = true
            } else {
                when (it.result?.status) {
                    Status.SUCCESS -> {
                        loadUserPreferences()
                    }
                    Status.ERROR -> it.result.errorThrowable?.errorMessage?.let { errorMessage ->
                        toastHelper.showToast(errorMessage)
                    }

                    null -> {
                        toastHelper.showToast("User data not found")
                    }
                }
                binding.swipeRefresh.isRefreshing = false
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        profileScreenViewModel.getMyStreams()
        profileScreenViewModel.myStreamsState.onEach {
            if (it.isLoading){
                binding.swipeRefresh.isRefreshing = true
            }
            else {
                if (it.result != null){
                    when(it.result.status){
                        Status.SUCCESS -> {
                            val previousStreams = it.result.data?.let { myStreamsResponse ->
                                myStreamsResponse.previous.map { stream->
                                        stream.toStreamItem()
                                }
                            }
                            streamItemListAdapter.submitList(previousStreams)
                        }
                        Status.ERROR -> it.result.errorThrowable?.let { error -> toastHelper.showToast("Error ${error.errorMessage}") }
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)



    }
}
