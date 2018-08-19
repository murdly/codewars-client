package com.akarbowy.codewarsclient.ui.challenges.viewmodel

import android.arch.paging.PagedList
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.ListingInfo
import com.akarbowy.codewarsclient.data.repository.challenges.NetworkState
import com.akarbowy.codewarsclient.helpers.SingleLiveEvent
import com.akarbowy.codewarsclient.ui.challenges.interactor.ChallengesInteractor
import com.akarbowy.codewarsclient.ui.challenges.router.ChallengesRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit


class ChallengesViewModel(
        interactor: ChallengesInteractor,
        router: ChallengesRouter
) : BaseViewModel<ChallengesInteractor, ChallengesRouter>(interactor, router) {

    val username = ObservableField<String>()

    val selectedTab = ObservableField<ChallengeTab>(ChallengeTab.COMPLETED)

    val completedList = ObservableField<PagedList<Challenge>>()

    val completedListingInfo = ObservableField<ListingInfo>()

    val completedNetworkState = ObservableField<NetworkState>()

    val authoredList = ObservableArrayList<Challenge>()

    val listEventHandler = BindingListEventHandler<Challenge>()

    val loadedZeroCompletedItems = ObservableBoolean(false)

    val isCompletedDataLoaded = ObservableBoolean(false)

    val loadedZeroAuthoredItems = ObservableBoolean(false)

    val isAuthoredDataLoaded = ObservableBoolean(false)

    val isAuthoredErrorOccurred = ObservableBoolean(false)

    val allDataLoadedEvent = SingleLiveEvent<Void>()

    init {

        initAuthoredListEventHandler()
    }

    private fun initAuthoredListEventHandler() {

        disposables += listEventHandler.clickObserver
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeBy(
                        onNext = { onAuthoredChallengeClicked(it) }
                )
    }

    fun start(username: String) {
        this.username.set(username)

        loadChallengesData(username)
    }

    private fun loadChallengesData(username: String) {

        val listing = interactor.loadCompletedChallenges(username) { completedChallengesCount() }

        disposables += listing.pagedList
                .subscribeBy(
                        onNext = { onCompletedChallengesLoaded(it) }
                )

        disposables += listing.info
                .subscribeBy(
                        onNext = { completedListingInfo.set(it) }
                )

        disposables += listing.networkState
                .subscribeBy(
                        onNext = { completedNetworkState.set(it) }
                )

        disposables += interactor.loadAuthoredChallenges(username)
                .subscribeBy(
                        onNext = { onAuthoredChallengesLoaded(it) },
                        onError = { onAuthoredDataError(it) }
                )
    }

    private fun onCompletedChallengesLoaded(challenges: PagedList<Challenge>) {

        completedList.set(challenges)

        completedListingInfo.get()?.let { info ->
            info.allDataFetched?.let {
                if (it) {
                    allDataLoadedEvent.call()
                }
            }
        }

        loadedZeroCompletedItems.set(completedList.get()?.isEmpty() == true)

        isCompletedDataLoaded.set(true)
    }

    private fun completedChallengesCount() = completedList.get()?.size ?: 0

    private fun onAuthoredChallengesLoaded(challenges: List<Challenge>) {

        loadedZeroAuthoredItems.set(challenges.isEmpty())

        authoredList.clear()
        authoredList.addAll(challenges)

        isAuthoredDataLoaded.set(true)
    }

    private fun onAuthoredDataError(error: Throwable) {
        isAuthoredDataLoaded.set(true)

        isAuthoredErrorOccurred.set(true)
    }

    private fun onAuthoredChallengeClicked(challenge: Challenge) {
        challenge.id?.let { router.loadChallengeScreen(it) }
    }

    val tabSelectionListener: (ChallengeTab) -> Unit = { selectedTab.set(it) }

    enum class ChallengeTab {
        COMPLETED, AUTHORED
    }
}

