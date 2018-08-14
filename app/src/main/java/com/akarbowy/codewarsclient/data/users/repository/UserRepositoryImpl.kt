package com.akarbowy.codewarsclient.data.users.repository

import com.akarbowy.codewarsclient.data.users.model.User
import com.akarbowy.codewarsclient.network.api.CodewarsApi
import com.akarbowy.codewarsclient.persistance.database.AppDatabase
import io.reactivex.Flowable
import io.reactivex.Single


class UserRepositoryImpl(
        private val api: CodewarsApi,
        private val database: AppDatabase
) : UserRepository {

    override fun searchUser(username: String): Single<User> {
        return Single.just(User())
    }

    override fun getSearches(count: Int): Flowable<User> {
        return Flowable.just(User())
    }

}
