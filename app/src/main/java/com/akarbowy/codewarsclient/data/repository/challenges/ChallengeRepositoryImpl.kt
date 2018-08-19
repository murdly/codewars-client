package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi.Companion.DEFAULT_PAGE_SIZE
import com.akarbowy.codewarsclient.data.network.model.AuthoredChallengeResponse
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.persistance.daos.ChallengeDao
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeMapper
import com.akarbowy.codewarsclient.data.persistance.entities.UserAuthoredChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserCompletedChallengeEntity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class ChallengeRepositoryImpl(
        private val api: CodewarsApi,
        private val challengeDao: ChallengeDao,
        private val mapper: ChallengeMapper
) : ChallengeRepository {

    private val networkState: PublishSubject<NetworkState> = PublishSubject.create()

    private val listingInfo: PublishSubject<ListingInfo> = PublishSubject.create()

    override fun loadCompletedChallenges(username: String, page: () -> Int): Listing<Challenge> {

        val dataSource = challengeDao.getUserCompletedChallenges(username)
                .map { Challenge(it.challengeId, it.name) }

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(DEFAULT_PAGE_SIZE / 2)
                .build()

        val builder = RxPagedListBuilder(dataSource, config)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Challenge>() {
                    override fun onZeroItemsLoaded() {
                        loadCompletedChallengesFromNetwork(username, page())
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
                        loadCompletedChallengesFromNetwork(username, page())
                    }
                })

        val pagedList = builder.buildFlowable(BackpressureStrategy.LATEST)

        return Listing<Challenge>(pagedList, listingInfo, networkState)
    }

    private fun loadCompletedChallengesFromNetwork(username: String, page: Int) {

        networkState.onNext(NetworkState.LOADING)

        api.getCompletedChallenges(username, page)
                .doOnSuccess {
                    networkState.onNext(NetworkState.LOADED)

                    val isLastPage = page + 1 == it.totalPages
                    listingInfo.onNext(ListingInfo(isLastPage))

                    cacheChallenges(username, it)
                }
                .doOnError { networkState.onNext(NetworkState.error(it.message)) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun loadAuthoredChallenges(username: String): Flowable<List<Challenge>> {

        return loadAuthoredFromDatabase(username)
                .flatMap { list ->
                    if (list.isEmpty()) {
                        loadAuthoredFromNetwork(username)
                    } else {
                        Flowable.just(list)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    private fun loadAuthoredFromDatabase(username: String): Flowable<List<Challenge>> {

        return challengeDao
                .getUserAuthoredChallenges(username)
                .flatMap { list ->
                    Flowable.just(list.map { Challenge(it.challengeId, it.name) })
                }
    }

    private fun loadAuthoredFromNetwork(username: String): Flowable<List<Challenge>> {

        return api.getAuthoredChallenges(username)
                .doOnSuccess { cacheChallenges(username, it) }
                .map { it.data.orEmpty() }
                .toFlowable()
    }


    override fun loadChallengeDetails(challengeId: String): Single<Challenge> {
        return api.getChallengeDetail(challengeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }


    private fun cacheChallenges(username: String, response: CompletedChallengeResponse) {

        val challenges = response.data?.map { mapper.from(it) }.orEmpty()

        challengeDao.insertChallenge(challenges)

        challenges.forEach {
            val entity = UserCompletedChallengeEntity(username, it.challengeId)
            challengeDao.insertUserCompletedChallenge(entity)
        }

    }

    private fun cacheChallenges(username: String, response: AuthoredChallengeResponse) {

        val challenges = response.data?.map { mapper.from(it) }.orEmpty()

        challengeDao.insertChallenge(challenges)

        challenges.forEach {
            val entity = UserAuthoredChallengeEntity(username, it.challengeId)
            challengeDao.insertUserAuthoredChallenge(entity)
        }

    }
}