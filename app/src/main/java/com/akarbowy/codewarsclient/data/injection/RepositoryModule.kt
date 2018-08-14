package com.akarbowy.codewarsclient.data.injection

import com.akarbowy.codewarsclient.data.users.repository.UserRepository
import com.akarbowy.codewarsclient.data.users.repository.UserRepositoryImpl
import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import com.akarbowy.codewarsclient.network.api.CodewarsApi
import com.akarbowy.codewarsclient.persistance.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun provideUserRepository(api: CodewarsApi, database: AppDatabase):
            UserRepository = UserRepositoryImpl(api, database)

}