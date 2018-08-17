package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PageKeyedDataSource
import com.akarbowy.codewarsclient.data.network.model.Challenge
import timber.log.Timber


class ChallengeDataSource() : PageKeyedDataSource<Int, Challenge>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Challenge>) {
        Timber.i("loadInitial")

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {
        Timber.i("loadAfter")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {
        Timber.i("loadBefore")

    }
}