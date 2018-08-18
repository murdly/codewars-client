package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.persistance.database.AppDatabase
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserCompletedChallengeEntity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


const val DEFAULT_PAGE_SIZE = 200

class ChallengeRepositoryImpl(
        private val api: CodewarsApi,
        private val database: AppDatabase
) : ChallengeRepository {

    var page = 0

    override fun loadCompletedChallenges(username: String): Flowable<PagedList<Challenge>> {

//        val boundaryCallback = ChallengeBoundaryCallback(username, api, page, this::cacheChallenges)
        page = 0

        val dataSourceFactory = database.challengeDao().getUserCompletedChallenges(username)
                .map { Challenge(it.challengeId, it.name) }
        val builder = RxPagedListBuilder(dataSourceFactory, DEFAULT_PAGE_SIZE / 2)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Challenge>() {
                    override fun onZeroItemsLoaded() {
                        Timber.i("onZeroItemsLoaded $page")

                        api.getCompletedChallenges(username, 0)
                                .doOnSuccess { cacheChallenges(username, it) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
                        Timber.i("onItemAtEndLoaded $page")
                        page++
                        api.getCompletedChallenges(username, page)
                                .doOnSuccess { cacheChallenges(username, it) }
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }

                })


        return builder.buildFlowable(BackpressureStrategy.LATEST)
    }

    private fun nextPage(count: Int): Int {
        return count / DEFAULT_PAGE_SIZE
    }

    override fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>> {

        return api.getAuthoredChallenges(username)
                .map { it.data ?: ArrayList() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toFlowable()
    }


    override fun loadChallengeDetails(challengeId: String): Single<Challenge> {
        return api.getChallengeDetail(challengeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }


    private fun cacheChallenges(username: String, response: CompletedChallengeResponse) {
        val challenges = response.data?.map { ChallengeEntity.Mapper.from(it) } ?: ArrayList()

        database.challengeDao().insertChallenge(challenges)

        challenges.forEach {
            val entity = UserCompletedChallengeEntity(username, it.challengeId)
            database.challengeDao().insertUserCompletedChallenge(entity)
        }

    }


}