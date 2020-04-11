package com.ashiqur.androidbaselib.util.helper

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/* Created by ashiq.buet16 **/

object DialogUtil {
    private var mLoader: AlertDialog? = null

    fun showBasicAlertDialog(
        activity: Activity,
        title: String?,
        msg: String?,
        btnPosText: String?,
        btnNegText: String?,
        btnBgColor: Int,
        taskSuccess: DialogInterface.OnClickListener?,
        taskCancelled: DialogInterface.OnClickListener?
    ) {
        val alertDialog =
            AlertDialog.Builder(activity).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnPosText, taskSuccess)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, btnNegText, taskCancelled)
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setBackgroundColor(activity.resources.getColor(btnBgColor))
        }
        alertDialog.show()
    }

    fun createCustomDialog(context: Context, bindingRoot: View, cancelable: Boolean = true): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setView(bindingRoot)
            .setCancelable(cancelable)
            .create()
    }

}