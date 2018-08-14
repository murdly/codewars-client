package com.akarbowy.codewarsclient.data.users.repository

import com.akarbowy.codewarsclient.data.users.model.User
import io.reactivex.Flowable
import io.reactivex.Single


interface UserRepository {

    fun searchUser(username: String): Single<User>

    fun getSearches(count: Int): Flowable<User>

}