package com.akarbowy.codewarsclient.ui.challenges.interactor

import android.arch.paging.PagedList
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import io.reactivex.Flowable
import io.reactivex.Single


interface ChallengesInteractor {

    fun loadCompletedChallenges(username: String): Flowable<PagedList<Challenge>>

}