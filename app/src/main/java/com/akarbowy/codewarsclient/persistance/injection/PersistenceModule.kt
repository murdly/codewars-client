package com.akarbowy.codewarsclient.persistance.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.akarbowy.codewarsclient.injection.qualifiers.ForApplication
import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import com.akarbowy.codewarsclient.persistance.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    @PerApplication
    fun provideDatabase(@ForApplication context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "CodewarsClient.db")
                .build()
    }
}