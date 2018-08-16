package com.akarbowy.codewarsclient.data.repository.users

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.persistance.database.AppDatabase
import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepositoryImpl(
        private val api: CodewarsApi,
        private val database: AppDatabase
) : UserRepository {

    override fun searchUser(username: String): Single<UserEntity> {
        return api.getUser(username)
                .map { UserEntity.Mapper.from(it) }
                .doOnSuccess { cacheUser(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun cacheUser(user: UserEntity?) {
        user?.let { entity ->
            database.userDao().insertUser(entity)
        }
    }

    override fun getSearches(count: Int): Flowable<List<UserEntity>> {
        return database.userDao().getUsers(count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
