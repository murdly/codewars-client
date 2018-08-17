package com.akarbowy.codewarsclient.ui.challenges.viewmodel

import android.arch.paging.PagedList
import android.databinding.ObservableField
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.ui.challenges.interactor.ChallengesInteractor
import com.akarbowy.codewarsclient.ui.challenges.router.ChallengesRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber


class ChallengesViewModel(
        interactor: ChallengesInteractor,
        router: ChallengesRouter
) : BaseViewModel<ChallengesInteractor, ChallengesRouter>(interactor, router) {


    val username = ObservableField<String>()

    val completedList = ObservableField<PagedList<Challenge>>()

    fun start(username: String) {
        this.username.set(username)
        loadChallengesData(username)
    }

    private fun loadChallengesData(username: String) {

        disposables += interactor.loadCompletedChallenges(username)
                .subscribeBy(
                        onNext = { onChallengesLoaded(it) }
                )
    }

    private fun onChallengesLoaded(challenges: PagedList<Challenge>) {
        Timber.i("Loaded ${challenges.size}")

        completedList.set(challenges)
    }

}