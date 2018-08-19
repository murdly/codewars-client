package com.akarbowy.codewarsclient.data.repository.injection

import com.akarbowy.codewarsclient.data.network.api.CodewarsApi
import com.akarbowy.codewarsclient.data.persistance.database.AppDatabase
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeMapper
import com.akarbowy.codewarsclient.data.persistance.entities.UserMapper
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepositoryImpl
import com.akarbowy.codewarsclient.data.repository.users.UserRepository
import com.akarbowy.codewarsclient.data.repository.users.UserRepositoryImpl
import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun provideUserRepository(api: CodewarsApi, database: AppDatabase):
            UserRepository = UserRepositoryImpl(api, database.userDao(), UserMapper())

    @Provides
    @PerApplication
    fun provideChallengeRepository(api: CodewarsApi, database: AppDatabase):
            ChallengeRepository = ChallengeRepositoryImpl(api, database.challengeDao(), ChallengeMapper())

}