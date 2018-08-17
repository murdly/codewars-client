package com.akarbowy.codewarsclient.ui.challenges.interactor

import android.arch.paging.PagedList
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import io.reactivex.Flowable


class ChallengesInteractorImpl(
        private val repository: ChallengeRepository
) : ChallengesInteractor {

    override fun loadCompletedChallenges(username: String): Flowable<PagedList<Challenge>> {
        return repository.loadCompletedChallenges(username)
    }

}