package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.persistance.database.AppDatabase
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
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

        val boundaryCallback = ChallengeBoundaryCallback(username, api, page, this::cacheChallenges)

        val dataSourceFactory = database.challengeDao().getChallenges()
                .map { Challenge(it.challengeId, it.name) }
        val builder = RxPagedListBuilder(dataSourceFactory, DEFAULT_PAGE_SIZE / 2)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Challenge>() {
                    override fun onZeroItemsLoaded() {
                        Timber.i("onZeroItemsLoaded $page")

                        api.getCompletedChallenges(username, 0)
                                .doOnSuccess { cacheChallenges(it) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
                        Timber.i("onItemAtEndLoaded $page")
                        Single.fromCallable { database.challengeDao().count() }
                                .flatMap {
                                    api.getCompletedChallenges(username, nextPage(it))
                                            .doOnSuccess { cacheChallenges(it) }
                                }
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }

                })


        return builder.buildFlowable(BackpressureStrategy.LATEST)
    }

    private fun nextPage(count: Int): Int {
        return count / DEFAULT_PAGE_SIZE
    }

    private fun cacheChallenges(response: CompletedChallengeResponse) {
        val r = response.data?.map { ChallengeEntity.Mapper.from(it) } ?: ArrayList()

        database.challengeDao().insertChallenge(r)

    }

}