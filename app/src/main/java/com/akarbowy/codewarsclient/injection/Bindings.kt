package com.akarbowy.codewarsclient.injection

import com.akarbowy.codewarsclient.injection.scopes.PerActivity
import com.akarbowy.codewarsclient.ui.search.injection.SearchModule
import com.akarbowy.codewarsclient.ui.search.view.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class Bindings {

    @PerActivity
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun bindSearchActivity(): SearchActivity

}
