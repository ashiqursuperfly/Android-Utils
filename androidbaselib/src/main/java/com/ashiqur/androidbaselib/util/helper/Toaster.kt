package com.ashiqur.androidbaselib.util.helper

import android.content.Context
import android.widget.Toast
import com.ashiqur.androidbaselib.base.BaseApplication


/* Created by ashiq.buet16 **/

object Toaster {

    fun showToast(message: String, context: Context = BaseApplication.getApplicationContext()) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun showToast(message: Int, context: Context = BaseApplication.getApplicationContext()) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun showLongToast(message: String, context: Context = BaseApplication.getApplicationContext()) {
        show(context, message, Toast.LENGTH_LONG)
    }

    fun showLongToast(message: Int, context: Context = BaseApplication.getApplicationContext()) {
        show(context, message, Toast.LENGTH_LONG)
    }

    private fun show(context: Context, message: String, length: Int) {
        Toast.makeText(context, message, length).show()
    }

    private fun show(context: Context, message: Int, length: Int) {
        Toast.makeText(context, message, length).show()
    }
}