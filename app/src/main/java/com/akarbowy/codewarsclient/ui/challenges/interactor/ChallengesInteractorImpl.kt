package com.akarbowy.codewarsclient.ui.challenges.interactor

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import com.akarbowy.codewarsclient.data.repository.challenges.Listing
import io.reactivex.Flowable


class ChallengesInteractorImpl(
        private val repository: ChallengeRepository
) : ChallengesInteractor {

    override fun loadCompletedChallenges(username: String, count: () -> Int): Listing<Challenge> {
        return repository.loadCompletedChallenges(username) { nextPage(count()) }
    }

    private fun nextPage(count: Int) = count / CodewarsApi.DEFAULT_PAGE_SIZE

    override fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>> {
        return repository.loadAuthoredChallenges(username)

    }

}