package com.imax.edumeet.presentation.screens.watch

import android.annotation.SuppressLint
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
import com.imax.ToastHelper
import com.imax.edumeet.R
import com.imax.edumeet.databinding.ScreenWatchBinding
import com.imax.edumeet.presentation.models.stream.LiveStreamItem
import com.imax.edumeet.presentation.models.stream.StreamItem
import com.imax.viewbinding.viewBinding
import org.koin.android.ext.android.inject

private const val ARG_STREAM_ITEM = "ARG_STREAM"
private const val ARG_LIVE_STREAM_ITEM = "ARG_LIVE_STREAM"
class WatchScreen: Fragment(R.layout.screen_watch) {
    private val binding by viewBinding<ScreenWatchBinding>()
    private var streamItem: StreamItem? = null
    private var liveStreamItem: LiveStreamItem? = null
    private val toastHelper by inject<ToastHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
               streamItem = it.getParcelable(ARG_STREAM_ITEM, StreamItem::class.java)
               liveStreamItem = it.getParcelable(ARG_LIVE_STREAM_ITEM, LiveStreamItem::class.java)
            } else {
               streamItem = it.getParcelable(ARG_STREAM_ITEM)
               liveStreamItem = it.getParcelable(ARG_LIVE_STREAM_ITEM)
            }
        }

        Log.d("StreamItem", "$streamItem")
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object: WebViewClient() {
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

        liveStreamItem?.let {
            binding.authorName.text = it.authorName
            binding.authorSubject.text = it.authorSubject
            binding.title.text = it.streamTitle

            Log.d("iframe live", it.streamUrl )
            val url = extractIframeUrl(it.streamUrl)
            url?.let { url ->
                binding.webView.loadUrl(url)
            }
        }

        streamItem?.let {
            binding.authorName.text = it.authorName
            binding.authorSubject.text = it.authorSubject
            binding.title.text = it.streamTitle

            val url = extractIframeUrl(it.streamUrl)
            url?.let { url ->
                binding.webView.loadUrl(url)
            }}

        binding.btnQuit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun extractIframeUrl(html: String): String? {
        val regex = """<iframe\s+[^>]*src=["']([^"']*)["'][^>]*>""".toRegex()
        val matchResult = regex.find(html)
        return matchResult?.groups?.get(1)?.value
    }



    companion object {
        fun newInstance(stream: StreamItem)  = WatchScreen().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_STREAM_ITEM, stream)
            }
        }

        fun newInstance(stream: LiveStreamItem)  = WatchScreen().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_LIVE_STREAM_ITEM, stream)
            }
        }
    }
}
