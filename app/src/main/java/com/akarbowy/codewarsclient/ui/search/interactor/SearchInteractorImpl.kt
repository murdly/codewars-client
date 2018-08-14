package com.akarbowy.codewarsclient.ui.search.interactor

import com.akarbowy.codewarsclient.data.users.model.User
import com.akarbowy.codewarsclient.data.users.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Single

const val NUMBER_OF_RECENT_SEARCHES = 5

class SearchInteractorImpl(
        private val repository: UserRepository
) : SearchInteractor {

    override fun searchUser(username: String): Single<User> {
        return repository.searchUser(username)
    }

    override fun getLastFiveSearches(): Flowable<User> {
        return repository.getSearches(NUMBER_OF_RECENT_SEARCHES)
    }
}