package com.imax.extensions

import android.view.View
import android.widget.EditText

object ViewExtensions {

    fun View.visible()  {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    val EditText.string : String get() = text.toString()
    val EditText.trimmedString: String get() = text.toString().trim()

}
