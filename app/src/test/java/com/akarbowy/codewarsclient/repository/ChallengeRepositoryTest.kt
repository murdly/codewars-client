package com.akarbowy.codewarsclient.repository

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.network.model.AuthoredChallengeResponse
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.persistance.daos.ChallengeDao
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeMapper
import com.akarbowy.codewarsclient.data.persistance.entities.UserAuthoredChallengeEntity
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepositoryImpl
import com.akarbowy.codewarsclient.helpers.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.*


class ChallengeRepositoryTest {

    private val challengeDao = mock(ChallengeDao::class.java)

    private val codewarsApi = mock(CodewarsApi::class.java)

    private val mapper: ChallengeMapper = mock(ChallengeMapper::class.java)

    private val repository = ChallengeRepositoryImpl(codewarsApi, challengeDao, mapper)

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun whenAuthoredInDatabaseDoesntGoToNetwork() {
        val username = "username"

        val enities = listOf(ChallengeEntity("id", "name"), ChallengeEntity("id1", "name1"))

        `when`(challengeDao.getUserAuthoredChallenges(username)).thenReturn(Flowable.just(enities))

        repository.loadAuthoredChallenges(username).subscribe()

        verify(codewarsApi, never()).getAuthoredChallenges(username)
    }


    @Test
    fun whenNoAuthoredInDatabaseGoesToNetwork() {
        val username = "username"

        val enities = emptyList<ChallengeEntity>()

        `when`(challengeDao.getUserAuthoredChallenges(username)).thenReturn(Flowable.just(enities))

        `when`(codewarsApi.getAuthoredChallenges("username")).thenReturn(Single.just(AuthoredChallengeResponse()))

        repository.loadAuthoredChallenges(username).subscribe()

        verify(codewarsApi).getAuthoredChallenges(username)
    }

    @Test
    fun cachesDataFromNetwork() {
        val username = "username"

        val enities = emptyList<ChallengeEntity>()

        val challenge = Challenge("id")

        val entity = ChallengeEntity("id", "name")

        val response = AuthoredChallengeResponse(listOf(challenge))

        `when`(challengeDao.getUserAuthoredChallenges(username)).thenReturn(Flowable.just(enities))

        `when`(codewarsApi.getAuthoredChallenges("username")).thenReturn(Single.just(response))

        `when`(mapper.from(challenge)).thenReturn(entity)

        repository.loadAuthoredChallenges(username).subscribe()

        verify(challengeDao).insertUserAuthoredChallenge(UserAuthoredChallengeEntity(username, challenge.id!!))
    }
}
