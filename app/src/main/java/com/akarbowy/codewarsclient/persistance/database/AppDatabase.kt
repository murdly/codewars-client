package com.akarbowy.codewarsclient.persistance.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.akarbowy.codewarsclient.persistance.converters.Converters
import com.akarbowy.codewarsclient.persistance.daos.UserDao
import com.akarbowy.codewarsclient.persistance.entities.UserEntity

@Database(version = 1,
        exportSchema = false,
        entities = [
            UserEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}