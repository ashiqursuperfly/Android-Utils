package com.ashiqur.androidbaselib.util.helper

import android.net.Uri
import android.widget.ImageView
import com.ashiqur.androidbaselib.R

fun ImageView.load(res: Int) {
    GlideUtil.load(res, R.drawable.ic_android_black_24dp, this)
}

fun ImageView.load(url: String?) {
    GlideUtil.load(url, R.drawable.ic_android_black_24dp, this)
}

fun ImageView.loadProfile(url: String?) {
    GlideUtil.load(url, R.drawable.ic_android_black_24dp, this)
}

fun ImageView.load(url: Uri?) {
    GlideUtil.load(url, R.drawable.ic_android_black_24dp, this)
}
