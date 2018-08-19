package com.akarbowy.codewarsclient.repository

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.User
import com.akarbowy.codewarsclient.data.persistance.daos.UserDao
import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserMapper
import com.akarbowy.codewarsclient.data.repository.users.UserRepositoryImpl
import com.akarbowy.codewarsclient.helpers.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.*


class UserRepositoryTest {

    private val userDao = mock(UserDao::class.java)

    private val codewarsApi = mock(CodewarsApi::class.java)

    private val mapper: UserMapper = mock(UserMapper::class.java)

    private val repository = UserRepositoryImpl(codewarsApi, userDao, mapper)

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun getUserFromNetworkAndCacheInDatabase() {

        val user = User("user")

        val entity = UserEntity("user")

        `when`(codewarsApi.getUser("user")).thenReturn(Single.just(user))

        `when`(mapper.from(user)).thenReturn(entity)

        repository.searchUser("user").subscribe()

        verify(codewarsApi).getUser("user")

        verify(userDao).insertUser(entity)
    }


    @Test
    fun loadRecentSearchesFromDatabase() {
        val count = 2

        val users = listOf(UserEntity("id"), UserEntity("id1"))

        `when`(userDao.getUsers(count)).thenReturn(Flowable.just(users))

        repository.getSearches(count)

        verify(userDao).getUsers(count)
    }
}
