package com.akarbowy.codewarsclient.ui.challenges.interactor

import android.arch.paging.PagedList
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.repository.challenges.Listing
import io.reactivex.Flowable
import io.reactivex.Single


interface ChallengesInteractor {

    fun loadCompletedChallenges(username: String): Listing<Challenge>

    fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>>

}