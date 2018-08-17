package com.akarbowy.codewarsclient.ui.challenges.viewmodel

import android.arch.paging.PagedList
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.support.annotation.StringRes
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge
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

    val authoredList = ObservableArrayList<Challenge>()

    val authoredListEventHandler = BindingListEventHandler<Challenge>()

    init {

        initAuthoredListEventHandler()
    }

    private fun initAuthoredListEventHandler() {

        disposables += authoredListEventHandler.clickObserver
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeBy {  }
    }

    fun start(username: String) {
        this.username.set(username)
        loadChallengesData(username)
    }

    private fun loadChallengesData(username: String) {

        disposables += interactor.loadCompletedChallenges(username)
                .subscribeBy(
                        onNext = { onCompletedChallengesLoaded(it) }
                )

        disposables += interactor.loadAuthoredChallenges(username)
                .subscribeBy(
                        onNext = { onAuthoredChallengesLoaded(it) }
                )
    }

    private fun onCompletedChallengesLoaded(challenges: PagedList<Challenge>) {
        Timber.i("Completed challenges loaded ${challenges.size}")

        completedList.set(challenges)
    }

    private fun onAuthoredChallengesLoaded(challenges: List<Challenge>) {
        Timber.i("Authored challenges loaded ${challenges.size}")

        authoredList.clear()
        authoredList.addAll(challenges)
    }

    val tabSelectionListener: (ChallengeTab) -> Unit = {
        Timber.i("Tab changed, id:$it")
        selectedTab.set(it)
    }

    enum class ChallengeTab {
        COMPLETED, AUTHORED
    }

}