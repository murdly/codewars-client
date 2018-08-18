package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject


data class Listing<T>(
        val pagedList: Flowable<PagedList<T>>,
        val networkState: PublishSubject<NetworkState>
)