package com.akarbowy.codewarsclient

import android.content.Context
import android.support.multidex.MultiDex
import com.akarbowy.codewarsclient.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var loggingTree: Timber.Tree

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        setupThirdParty()
        setupDebugTools()
    }

    private fun setupThirdParty() {
        initRxJavaPlugins()
    }

    private fun setupDebugTools() {
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(loggingTree)
    }

    private fun initRxJavaPlugins() {
        RxJavaPlugins.setErrorHandler { error -> Timber.e(error, "Undeliverable exception reported.") }
    }
}