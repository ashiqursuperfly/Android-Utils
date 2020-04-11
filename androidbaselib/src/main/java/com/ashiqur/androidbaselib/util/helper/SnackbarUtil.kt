package com.ashiqur.androidbaselib.util.helper

import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ashiqur.androidbaselib.R
import com.ashiqur.androidbaselib.base.BaseApplication
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun showLongSnackBar(
        view: View,
        message: String,
        colorRes: Int = R.color.colorPrimary
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(BaseApplication.getApplicationContext(), colorRes))
            snackbar.show()
        }
        else
            Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortSnackBar(
        view: View,
        message: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(BaseApplication.getApplicationContext(), R.color.colorPrimary))
            snackbar.show()
        }
        else
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
    }
}