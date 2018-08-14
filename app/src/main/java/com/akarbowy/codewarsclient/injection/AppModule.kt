package com.akarbowy.codewarsclient.injection

import android.content.Context
import com.akarbowy.codewarsclient.App
import com.akarbowy.codewarsclient.injection.qualifiers.ForApplication
import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @PerApplication
    @ForApplication
    fun provideContext(application: App): Context = application

}
