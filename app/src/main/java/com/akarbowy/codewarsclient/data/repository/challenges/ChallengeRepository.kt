package com.akarbowy.codewarsclient.data.repository.challenges

import com.akarbowy.codewarsclient.data.network.model.Challenge
import io.reactivex.Flowable
import io.reactivex.Single


interface ChallengeRepository {

    fun loadCompletedChallenges(username: String, page: () -> Int): Listing<Challenge>

    fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>>

    fun loadChallengeDetails(challengeId: String): Single<Challenge>

}