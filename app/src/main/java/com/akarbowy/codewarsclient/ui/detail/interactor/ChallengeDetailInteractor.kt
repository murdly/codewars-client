package com.akarbowy.codewarsclient.ui.detail.interactor

import com.akarbowy.codewarsclient.data.network.model.Challenge
import io.reactivex.Single


interface ChallengeDetailInteractor {

    fun loadChallengeDetails(challengeId: String): Single<Challenge>

}