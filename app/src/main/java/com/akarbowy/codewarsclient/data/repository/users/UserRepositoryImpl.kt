package com.akarbowy.codewarsclient.data.repository.users

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.persistance.daos.UserDao
import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserMapper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepositoryImpl(
        private val api: CodewarsApi,
        private val userDao: UserDao,
        private val mapper: UserMapper
) : UserRepository {

    override fun searchUser(username: String): Single<UserEntity> {
        return api.getUser(username)
                .map { mapper.from(it) }
                .doOnSuccess { cacheUser(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun cacheUser(user: UserEntity?) {
        user?.let { entity ->
            userDao.insertUser(entity)
        }
    }

    override fun getSearches(count: Int): Flowable<List<UserEntity>> {
        return userDao.getUsers(count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
