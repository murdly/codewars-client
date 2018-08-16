package com.akarbowy.codewarsclient.data.repository.users

import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single


interface UserRepository {

    fun searchUser(username: String): Single<UserEntity>

    fun getSearches(count: Int): Flowable<List<UserEntity>>

}