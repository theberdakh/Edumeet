package com.imax.edumeet.presentation.screens.stream

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionsRequester(private val activity: FragmentActivity) {

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
        if (permissions.all {
                it.value
            }) {
            onPermissionsGranted?.invoke()
            //All granted
        } else {
            onPermissionsDenied?.invoke()
        }
    }

    private var onPermissionsGranted: (() -> Unit)? = null
    private var onPermissionsDenied: (() -> Unit)? = null

    fun request(permissions: List<String>,onGranted: () -> Unit, onDenied: () -> Unit){
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (permissionsToRequest.isEmpty()){
            onGranted()
        }
        else {
            onPermissionsGranted = onGranted
            onPermissionsDenied = onDenied

            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}
