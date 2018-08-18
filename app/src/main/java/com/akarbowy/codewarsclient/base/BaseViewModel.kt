package com.akarbowy.codewarsclient.base

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel<I, R>(
        protected var interactor: I,
        protected var router: R
) : ViewModel() {

    protected val disposables = CompositeDisposable()

    var isDataLoaded = ObservableBoolean(false)

    var isErrorOnDataLoad = ObservableBoolean(false)

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    protected fun displayError(error: Throwable, sender: Any) {
        Timber.w(error)

        isDataLoaded.set(true)
        isErrorOnDataLoad.set(true)
    }

    protected open fun loadData() {
        isDataLoaded.set(false)
        isErrorOnDataLoad.set(false)
    }

    protected open fun onDataLoaded() {
        isDataLoaded.set(true)
    }

    protected fun onErrorOccurred(error: Throwable) {
        displayError(error, this)
    }

}
