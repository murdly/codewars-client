package com.akarbowy.codewarsclient.ui.challenges.interactor

import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.Listing
import io.reactivex.Flowable


interface ChallengesInteractor {

    fun loadCompletedChallenges(username: String, count: () -> Int): Listing<Challenge>

    fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>>

}