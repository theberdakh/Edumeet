package com.imax.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.imax.edumeet.R

class AlertDialogHelper(private val context: Context) {
    private val builder = MaterialAlertDialogBuilder(context)

    fun showAlertDialog(@StringRes message: Int, @StringRes title: Int, @StringRes positiveButton: Int, @StringRes negativeButton: Int, positiveButtonClick: DialogInterface.OnClickListener, negativeButtonClick: DialogInterface.OnClickListener) {
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton, positiveButtonClick)
        builder.setNegativeButton(negativeButton, negativeButtonClick)
        builder.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_dialog))
        builder.create()
        builder.show()
    }

    fun showAlertDialog(message: String, @StringRes title: Int, @StringRes positiveButton: Int, @StringRes negativeButton: Int, positiveButtonClick: DialogInterface.OnClickListener, negativeButtonClick: DialogInterface.OnClickListener) {
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton, positiveButtonClick)
        builder.setNegativeButton(negativeButton, negativeButtonClick)
        builder.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_dialog))
        builder.create()
        builder.show()
    }


}
