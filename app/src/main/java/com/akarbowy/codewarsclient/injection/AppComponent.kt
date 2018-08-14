package com.akarbowy.codewarsclient.injection

import com.akarbowy.codewarsclient.App
import com.akarbowy.codewarsclient.base.BaseModule
import com.akarbowy.codewarsclient.data.injection.RepositoryModule
import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import com.akarbowy.codewarsclient.network.injection.NetworkModule
import com.akarbowy.codewarsclient.persistance.injection.PersistenceModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [
    AndroidSupportInjectionModule::class,
    Bindings::class,
    AppModule::class,
    BaseModule::class,
    NetworkModule::class,
    PersistenceModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
