package com.akarbowy.codewarsclient.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

}
