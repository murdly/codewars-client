package com.akarbowy.codewarsclient.ui.detail.interactor

import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import io.reactivex.Single


class ChallengeDetailInteractorImpl(
        private val repository: ChallengeRepository
) : ChallengeDetailInteractor {

    override fun loadChallengeDetails(challengeId: String): Single<Challenge> {
        return repository.loadChallengeDetails(challengeId)
    }
}