package com.imax.edumeet.presentation.screens.watch

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.imax.edumeet.R
import com.imax.edumeet.data.remote.models.Status
import com.imax.edumeet.data.remote.models.errorMessage
import com.imax.edumeet.databinding.ScreenWatchBinding
import com.imax.edumeet.presentation.adapter.FeedbackItemListAdapter
import com.imax.edumeet.presentation.models.stream.StreamItem
import com.imax.extensions.ViewExtensions.gone
import com.imax.extensions.ViewExtensions.invisible
import com.imax.extensions.ViewExtensions.visible
import com.imax.toast.ToastHelper
import com.imax.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_STREAM_ITEM = "ARG_STREAM"
class WatchSelfVideos: Fragment(R.layout.screen_watch) {
    private val viewModel by viewModel<WatchScreenViewModel>()
    private val binding by viewBinding<ScreenWatchBinding>()
    private var streamItem: StreamItem? = null
    private val toastHelper by inject<ToastHelper>()
    private val feedbackItemListAdapter = FeedbackItemListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            streamItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                it.getParcelable(ARG_STREAM_ITEM, StreamItem::class.java)
            } else {
                it.getParcelable(ARG_STREAM_ITEM)
            }
        }

        Log.d("StreamItem", "$streamItem")
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        binding.feedbackList.adapter = feedbackItemListAdapter
        binding.layoutFeedback.gone()
        binding.ratingBarLayout.gone()

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object: WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progress.visible()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progress.gone()
                }


                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.d("Log", "$error")
                    error?.let {
                        Log.d("Log", "$error")
                        toastHelper.showToast("${it.errorCode}: ${it.description}")
                    }
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.d("Lo2", "$errorResponse")
                    errorResponse?.let {
                        Log.d("Lo2", "$errorResponse")
                        toastHelper.showToast("${it.statusCode}: ${it.reasonPhrase}")
                    }
                }

            }
        }



        streamItem?.let {
            viewModel.getVideo(it.streamId)
            binding.authorName.text = it.authorName
            binding.authorSubject.text = it.authorSubject
            binding.title.text = it.streamTitle
            viewModel.getFeedbacks(it.id)

            Glide.with(requireContext())
                .load(it.authorProfile)
                .circleCrop()
                .placeholder(R.drawable.ic_profile_fill)
                .into(binding.authorImage)

        }


        binding.progress.invisible()


        binding.btnQuit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {
        viewModel.videoState.onEach {
            Log.i("Stream", "VideoState $it")
            if (it.isLoading) {
                binding.progress.visible()
            }
            if (!it.isLoading && it.result != null){
                binding.progress.gone()
                when(it.result.status){
                    Status.SUCCESS -> it.result.data?.let { videoResponse ->
                        binding.webView.loadUrl(videoResponse.player)
                    }
                    Status.ERROR -> it.result.errorThrowable?.let {error -> toastHelper.showToast(
                        error.errorMessage
                    ) }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.feedbacksState.onEach {
            if (it.isLoading) {
                Log.i("TeacherFeedback", "Loading")
            }
            if (!it.isLoading && it.result != null){
                when(it.result.status){
                    Status.SUCCESS -> it.result.data?.let { feedbacks ->
                        feedbackItemListAdapter.submitList(feedbacks)
                        feedbackItemListAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> it.result.errorThrowable?.let {error -> toastHelper.showToast(
                        error.errorMessage
                    ) }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    companion object {
        fun newInstance(stream: StreamItem)  = WatchSelfVideos().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_STREAM_ITEM, stream)
            }
        }

    }
}

