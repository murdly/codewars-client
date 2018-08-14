package com.akarbowy.codewarsclient.base

import android.content.Context
import android.content.Intent

open class BaseRouter(protected var context: Context?) {

    protected fun loadActivity(activityClass: Class<*>) {
        val activityIntent = getActivityIntent(activityClass)
        context?.startActivity(activityIntent)
    }

    protected fun loadActivity(activityIntent: Intent) {
        context?.startActivity(activityIntent)
    }

    protected fun getActivityIntent(activityClass: Class<*>): Intent {
        return Intent(context, activityClass)
    }
}
