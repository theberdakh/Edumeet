package com.imax.edumeet.presentation.screens.stream

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.imax.ToastHelper
import com.imax.edumeet.databinding.ScreenStreamBinding
import org.koin.android.ext.android.inject
import video.api.livestream.ApiVideoLiveStream
import video.api.livestream.enums.Resolution
import video.api.livestream.interfaces.IConnectionListener
import video.api.livestream.models.AudioConfig
import video.api.livestream.models.VideoConfig

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


        requestPermissions()
        val connectionListener = object : IConnectionListener {
            override fun onConnectionFailed(reason: String) {
                Log.e("Error", "Failed: $reason")
            }

            override fun onConnectionSuccess() {
                Log.i("Connection", "Success")
            }


            override fun onDisconnect() {
                Log.i("Connection", "Disconnected")

            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        val apiVideoView = binding.apiVideoView
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        apiVideo = ApiVideoLiveStream(
            context = this,
            connectionListener = connectionListener,
            initialAudioConfig = audioConfig,
            initialVideoConfig = videoConfig,
            apiVideoView = apiVideoView
        )

        streamKey?.let {
            apiVideo.startStreaming(it, "rtmps://broadcast.api.video:1936/s")
        }

    }
}
