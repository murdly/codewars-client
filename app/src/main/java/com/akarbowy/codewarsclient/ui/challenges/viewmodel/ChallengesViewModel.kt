package com.akarbowy.codewarsclient.ui.challenges.viewmodel

import android.arch.paging.PagedList
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.NetworkState
import com.akarbowy.codewarsclient.helpers.SingleLiveEvent
import com.akarbowy.codewarsclient.ui.challenges.interactor.ChallengesInteractor
import com.akarbowy.codewarsclient.ui.challenges.router.ChallengesRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit


class ChallengesViewModel(
        interactor: ChallengesInteractor,
        router: ChallengesRouter
) : BaseViewModel<ChallengesInteractor, ChallengesRouter>(interactor, router) {

    val username = ObservableField<String>()

    val selectedTab = ObservableField<ChallengeTab>(ChallengeTab.COMPLETED)

    val completedList = ObservableField<PagedList<Challenge>>()

    val completedNetworkState = ObservableField<NetworkState>()

    val authoredList = ObservableArrayList<Challenge>()

    val listEventHandler = BindingListEventHandler<Challenge>()

    val loadedZeroCompletedItems = ObservableBoolean(false)

    val loadedZeroAuthoredItems = ObservableBoolean(false)

    val errorEvent = SingleLiveEvent<Void>()

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

        val listing = interactor.loadCompletedChallenges(username)

        disposables += listing.pagedList
                .subscribeBy(
                        onNext = { onCompletedChallengesLoaded(it) }
                )

        disposables += listing.networkState
                .subscribeBy(
                        onNext = { completedNetworkState.set(it) }
                )

        disposables += interactor.loadAuthoredChallenges(username)
                .subscribeBy(
                        onNext = { onAuthoredChallengesLoaded(it) }
                )
    }

    private fun onCompletedChallengesLoaded(challenges: PagedList<Challenge>) {
        Timber.i("Completed challenges loaded ${challenges.size}")

        loadedZeroCompletedItems.set(challenges.isEmpty()) //only if more is not being loaded

        completedList.set(challenges)

        onDataLoaded()
    }

    private fun onAuthoredChallengesLoaded(challenges: List<Challenge>) {
        Timber.i("Authored challenges loaded ${challenges.size}")

        loadedZeroAuthoredItems.set(challenges.isEmpty())

        authoredList.clear()
        authoredList.addAll(challenges)

        onDataLoaded()
    }

    private fun onAuthoredChallengeClicked(challenge: Challenge) {
        challenge.id?.let { router.loadChallengeScreen(it) }
    }

    val tabSelectionListener: (ChallengeTab) -> Unit = { selectedTab.set(it) }

    enum class ChallengeTab {
        COMPLETED, AUTHORED
    }

}