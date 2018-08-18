package com.akarbowy.codewarsclient.data.repository.challenges

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.AuthoredChallengeResponse
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.persistance.database.AppDatabase
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserAuthoredChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserCompletedChallengeEntity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


const val DEFAULT_PAGE_SIZE = 200

const val INITIAL_PAGE_INDEX = 0

class ChallengeRepositoryImpl(
        private val api: CodewarsApi,
        private val database: AppDatabase
) : ChallengeRepository {

    var page = INITIAL_PAGE_INDEX

    override fun loadCompletedChallenges(username: String): Listing<Challenge> {

        page = INITIAL_PAGE_INDEX

        val dataSourceFactory = database.challengeDao().getUserCompletedChallenges(username)
                .map { Challenge(it.challengeId, it.name) }
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(DEFAULT_PAGE_SIZE / 2)
                .build()

        val builder = RxPagedListBuilder(dataSourceFactory, config)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Challenge>() {
                    override fun onZeroItemsLoaded() {
                        Timber.i("onZeroItemsLoaded $page")

                        networkState.onNext(NetworkState.LOADING)

                        loadCompletedChallengesFromNetwork(username, 0)
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
                        Timber.i("onItemAtEndLoaded $page")

                        networkState.onNext(NetworkState.LOADING)

                        page++

                        loadCompletedChallengesFromNetwork(username, page)
                    }
                })


        val pagedList = builder.buildFlowable(BackpressureStrategy.LATEST)

        return Listing<Challenge>(pagedList, networkState)
    }

    private fun loadCompletedChallengesFromNetwork(username: String, page: Int) {
        api.getCompletedChallenges(username, page)
                .doOnSuccess {
                    networkState.onNext(NetworkState.LOADED)

                    cacheChallenges(username, it)
                }
                .doOnError { networkState.onNext(NetworkState.error(it.message)) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private val networkState: PublishSubject<NetworkState> = PublishSubject.create()

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
        Timber.i("LOADING FROM DATABASE")

        return database.challengeDao()
                .getUserAuthoredChallenges(username)
                .flatMap { list ->
                    Flowable.just(list.map { Challenge(it.challengeId, it.name) })
                }
    }

    private fun loadAuthoredFromNetwork(username: String): Flowable<List<Challenge>> {
        Timber.i("LOADING FROM NETWORK")

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

        val challenges = response.data?.map { ChallengeEntity.Mapper.from(it) }.orEmpty()

        database.challengeDao().insertChallenge(challenges)

        challenges.forEach {
            val entity = UserCompletedChallengeEntity(username, it.challengeId)
            database.challengeDao().insertUserCompletedChallenge(entity)
        }

    }

    private fun cacheChallenges(username: String, response: AuthoredChallengeResponse) {
        Timber.i("CHACHING AUTHORED CHALLENGES")

        val challenges = response.data?.map { ChallengeEntity.Mapper.from(it) } ?: ArrayList()

        database.challengeDao().insertChallenge(challenges)

        challenges.forEach {
            val entity = UserAuthoredChallengeEntity(username, it.challengeId)
            database.challengeDao().insertUserAuthoredChallenge(entity)
        }

    }


}