package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class ChallengeBoundaryCallback(
        private val username: String,
        private val api: CodewarsApi,
        private val page: Int,
        private val handleResponse: (String, CompletedChallengeResponse) -> Unit
) : PagedList.BoundaryCallback<Challenge>() {

    override fun onZeroItemsLoaded() {

        api.getCompletedChallenges(username, page)
                .doOnSuccess { handleResponse(username, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        Timber.i("TAK $page")
    }
}