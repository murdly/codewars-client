package com.akarbowy.codewarsclient.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider


abstract class ViewModelProviderFactory<VM: BaseViewModel<*, *>> : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }

    protected abstract fun create(): VM
}
