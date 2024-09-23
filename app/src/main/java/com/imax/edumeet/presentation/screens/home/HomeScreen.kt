package com.imax.edumeet.presentation.screens.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.imax.toast.ToastHelper
import com.imax.edumeet.R
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.errorMessage
import com.imax.edumeet.databinding.ScreenHomeBinding
import com.imax.edumeet.presentation.adapter.LiveStreamItemListAdapter
import com.imax.edumeet.presentation.adapter.StreamItemListAdapter
import com.imax.edumeet.presentation.screens.watch.WatchScreen
import com.imax.navigation.NavigationExtensions.addFragmentToBackStack
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding<ScreenHomeBinding>()
    private val homeScreenViewModel by viewModel<HomeScreenViewModel>()
    private val liveStreamItemListAdapter by lazy { LiveStreamItemListAdapter() }
    private val streamItemListAdapter by lazy { StreamItemListAdapter() }

    private val toastHelper by inject<ToastHelper>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeHomeScreenViewModel()

        binding.rvLiveStreams.adapter = liveStreamItemListAdapter
        binding.rvPlannedStreams.adapter = streamItemListAdapter

        binding.swipeRefresh.setOnRefreshListener {
            observeHomeScreenViewModel()
        }

        liveStreamItemListAdapter.setOnLiveStreamItemClickListener { liveStreamItem ->
            requireActivity().supportFragmentManager.addFragmentToBackStack(R.id.activity_main_container, WatchScreen.newInstance(liveStreamItem) )
        }
        streamItemListAdapter.setOnStreamItemClickListener { streamItem ->
            requireActivity().supportFragmentManager.addFragmentToBackStack(R.id.activity_main_container, WatchScreen.newInstance(streamItem) )
        }

    }

    private fun observeHomeScreenViewModel() {

        homeScreenViewModel.getPreviousStreams()
        homeScreenViewModel.allStreamsState.onEach {
            if (it.isLoading) {
                binding.swipeRefresh.isRefreshing = true
            } else {
                when (it.result?.status) {
                    Status.SUCCESS -> {
                        streamItemListAdapter.submitList(null)
                        streamItemListAdapter.submitList(it.result.data)
                    }

                    Status.ERROR -> it.result.errorThrowable?.errorMessage?.let { errorMessage ->
                        toastHelper.showToast(errorMessage)
                        Log.e("Log", errorMessage)
                    }

                    null -> {
                        toastHelper.showToast("null")
                    }
                }
                binding.swipeRefresh.isRefreshing = false
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeScreenViewModel.getLiveStreams()
        homeScreenViewModel.liveStreamsState.onEach {
                    if (it.isLoading) {
                      binding.swipeRefresh.isRefreshing = true
                    } else {
                        when (it.result?.status) {
                            Status.SUCCESS -> {
                                liveStreamItemListAdapter.submitList(null)
                                liveStreamItemListAdapter.submitList(it.result.data)
                            }

                            Status.ERROR -> it.result.errorThrowable?.errorMessage?.let { errorMessage ->
                                toastHelper.showToast(errorMessage)
                                Log.d("Log", errorMessage)
                            }

                            null -> {toastHelper.showToast("null")}          }
                       binding.swipeRefresh.isRefreshing = false
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
}
