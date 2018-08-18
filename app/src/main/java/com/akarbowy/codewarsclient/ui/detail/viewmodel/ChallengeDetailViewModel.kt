package com.akarbowy.codewarsclient.ui.detail.viewmodel

import android.databinding.ObservableField
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.ui.detail.interactor.ChallengeDetailInteractor
import com.akarbowy.codewarsclient.ui.detail.router.ChallengeDetailRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy


class ChallengeDetailViewModel(
        interactor: ChallengeDetailInteractor,
        router: ChallengeDetailRouter
) : BaseViewModel<ChallengeDetailInteractor, ChallengeDetailRouter>(interactor, router) {

    val challenge = ObservableField<Challenge>()

    lateinit var challengeId: String

    fun start(challengeId: String) {
        this.challengeId = challengeId

        loadData()
    }

    override fun loadData() {
        super.loadData()

        disposables += interactor.loadChallengeDetails(challengeId)
                .subscribeBy(
                        onSuccess = { onDetailsLoaded(it) },
                        onError = { onErrorOccurred(it) }
                )
    }

    private fun onDetailsLoaded(challenge: Challenge) {
        onDataLoaded()

        this.challenge.set(challenge)
    }
}
