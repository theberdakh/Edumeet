package com.imax

import android.content.Context
import android.widget.Toast

class ToastHelper(private val context: Context) {

    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, duration).show()
    }
}
