package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject


data class Listing<T>(
        val pagedList: Flowable<PagedList<T>>,
        val info: PublishSubject<ListingInfo>,
        val networkState: PublishSubject<NetworkState>
)

data class ListingInfo(
        val allDataFetched: Boolean? = null
)