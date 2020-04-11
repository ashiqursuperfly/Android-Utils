package com.ashiqur.superfly

import com.ashiqur.androidbaselib.base.BaseActivity
import com.ashiqur.androidbaselib.util.helper.Toaster

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarId(): Int {
        return NO_TOOLBAR
    }

    override fun afterOnCreate() {
        Toaster.showToast("Hello!")
    }

}
