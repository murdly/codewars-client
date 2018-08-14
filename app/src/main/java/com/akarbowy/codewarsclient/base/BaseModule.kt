package com.akarbowy.codewarsclient.base

import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides
import timber.log.Timber


@Module
class BaseModule {

    @Provides
    @PerApplication
    fun provideLogger(): Timber.Tree = Timber.DebugTree()

}
