package com.akarbowy.codewarsclient.ui.search.interactor

import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import com.akarbowy.codewarsclient.data.repository.users.UserRepository
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Flowable
import io.reactivex.Single

const val NUMBER_OF_RECENT_SEARCHES = 5

class SearchInteractorImpl(
        private val repository: UserRepository
) : SearchInteractor {

    override fun searchUser(username: String): Single<SearchViewModel.SearchResult> {
        return repository.searchUser(username)
                .map { toUserData(it) }
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 404) {
                        Single.just(SearchViewModel.SearchResult.NoUser)
                    } else {
                        Single.error(it)
                    }
                }

    }

    override fun getLastFiveSearches(): Flowable<List<SearchViewModel.SearchResult>> {
        return repository.getSearches(NUMBER_OF_RECENT_SEARCHES)
                .flatMap { list ->
                    Flowable.just(list.map { toUserData(it) })
                }
    }

    private fun toUserData(user: UserEntity): SearchViewModel.SearchResult {

        return SearchViewModel.SearchResult.UserData(user.userId, user.position, user.bestLanguage)
    }

}