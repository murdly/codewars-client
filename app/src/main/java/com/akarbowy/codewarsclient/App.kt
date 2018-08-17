package com.akarbowy.codewarsclient

import com.akarbowy.codewarsclient.injection.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var loggingTree: Timber.Tree

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        setupThirdParty()
        setupDebugTools()
    }

    private fun setupThirdParty() {
        initDateTime()
        initRxJavaPlugins()
    }

    private fun setupDebugTools() {
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(loggingTree)
    }

    private fun initDateTime() {
        AndroidThreeTen.init(this)

    }

    private fun initRxJavaPlugins() {
        RxJavaPlugins.setErrorHandler { error -> Timber.e(error, "Undeliverable exception reported.") }
    }
}