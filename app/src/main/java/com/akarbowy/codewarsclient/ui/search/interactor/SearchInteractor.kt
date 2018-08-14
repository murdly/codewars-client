package com.akarbowy.codewarsclient.ui.search.interactor

import com.akarbowy.codewarsclient.data.users.model.User
import io.reactivex.Flowable
import io.reactivex.Single


interface SearchInteractor {

    fun searchUser(username: String): Single<User>

    fun getLastFiveSearches(): Flowable<User>
}