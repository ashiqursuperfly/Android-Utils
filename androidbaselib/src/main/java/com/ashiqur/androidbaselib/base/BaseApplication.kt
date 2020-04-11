package com.ashiqur.androidbaselib.base

/* Created by ashiq.buet16 **/

import android.content.Context
import androidx.multidex.MultiDexApplication

abstract class BaseApplication : MultiDexApplication() {

    init {
        sInstance = this
    }

    companion object {
        private lateinit var sInstance: BaseApplication

        fun getApplicationContext(): Context {
            return sInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        afterOnCreate()
    }

    abstract fun afterOnCreate()

}