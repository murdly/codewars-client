package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import io.reactivex.Flowable
import io.reactivex.Single


interface ChallengeRepository {

    fun loadCompletedChallenges(username: String): Flowable<PagedList<Challenge>>

    fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>>

}