package com.imax.edumeet.presentation.screens.stream

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.imax.dialog.AlertDialogHelper
import com.imax.edumeet.R
import com.imax.toast.ToastHelper
import com.imax.edumeet.databinding.ScreenStreamBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import video.api.livestream.ApiVideoLiveStream
import video.api.livestream.enums.Resolution
import video.api.livestream.interfaces.IConnectionListener
import video.api.livestream.models.AudioConfig
import video.api.livestream.models.VideoConfig
import video.api.livestream.views.ApiVideoView

private const val ARG_STREAM_KEY = "STREAM_KEY"

class StreamScreen : AppCompatActivity() {
    private lateinit var binding: ScreenStreamBinding
    private lateinit var apiVideo: ApiVideoLiveStream
    private val toastHelper by inject<ToastHelper>()
    private var streamKey: String? = ""



    private val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private val permissionsA13 = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
    )

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasPermissions(this)) {
                ActivityCompat.requestPermissions(this, permissionsA13, 1)
            }
        } else {
            if (!hasPermissions(this)) {
                ActivityCompat.requestPermissions(this, permissions, 1)
            }
        }
    }

    private fun hasPermissions(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasPermissions(context, *permissionsA13)
        } else {
            hasPermissions(context, *permissions)
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context, permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        streamKey = intent.getStringExtra(ARG_STREAM_KEY)


        val alertDialogHelper = AlertDialogHelper(this)
        binding.close.setOnClickListener {
            alertDialogHelper.showAlertDialog(
                message = R.string.dialog_message_close_stream,
                title = R.string.dialog_title_close_stream,
                positiveButton = R.string.stop_streaming,
                negativeButton = R.string.cancel,
                positiveButtonClick = { dialog, which ->
                    this.finish()
                },
                negativeButtonClick = { dialog, which ->
                    dialog.cancel()
                })
        }

        requestPermissions()

        streamKey?.let {
            Log.i("StreamScreen", it)
            startStreaming()
        }

    }

    private fun startStreaming() {

        val alertDialogHelper = AlertDialogHelper(this)

        val connectionListener = object : IConnectionListener {
            override fun onConnectionFailed(reason: String) {
                runOnUiThread {
                    toastHelper.showToast(reason)
                    alertDialogHelper.showAlertDialog(
                        message = reason,
                        title = R.string.dialog_title_failed,
                        positiveButton = R.string.retry,
                        negativeButton = R.string.end_livestream,
                        positiveButtonClick = { dialog, which ->
                            startStreaming()
                        },
                        negativeButtonClick = { dialog, which ->
                            this@StreamScreen.finish()
                        })
                }
            }

            override fun onConnectionSuccess() {
                runOnUiThread{
                    toastHelper.showToast("You are live now!")
                }
            }


            override fun onDisconnect() {
                runOnUiThread {
                    alertDialogHelper.showAlertDialog(
                        message = R.string.dialog_message_disconnected,
                        title = R.string.dialog_title_disconnected,
                        positiveButton = R.string.retry,
                        negativeButton = R.string.end_livestream,
                        positiveButtonClick = { dialog, which ->
                            startStreaming()
                        },
                        negativeButtonClick = { dialog, which ->
                            this@StreamScreen.finish()
                        })
                    toastHelper.showToast("Live broadcasting disconnected!")
                }
                Log.i("Connection", "Disconnected")

            }
        }
        val audioConfig = AudioConfig(
            bitrate = 128 * 1000, // 128 kbps
            sampleRate = 44100, // 44.1 kHz
            stereo = true,
            echoCanceler = true,
            noiseSuppressor = true
        )
        val videoConfig = VideoConfig(
            bitrate = 2 * 1000 * 1000, // 2 Mbps
            resolution = Resolution.RESOLUTION_720.size,
            fps = 30
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        apiVideo = ApiVideoLiveStream(
            context = this,
            connectionListener = connectionListener,
            initialAudioConfig = audioConfig,
            initialVideoConfig = videoConfig,
            apiVideoView = binding.apiVideoView
        )

        streamKey?.let {
            apiVideo.startStreaming(it, "rtmp://broadcast.api.video/s")
        }

    }
}
