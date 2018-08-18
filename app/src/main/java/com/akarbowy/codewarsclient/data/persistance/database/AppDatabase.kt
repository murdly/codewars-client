package com.akarbowy.codewarsclient.data.persistance.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.akarbowy.codewarsclient.data.persistance.converters.Converters
import com.akarbowy.codewarsclient.data.persistance.daos.ChallengeDao
import com.akarbowy.codewarsclient.data.persistance.daos.UserDao
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserCompletedChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity

@Database(version = 1,
        exportSchema = false,
        entities = [
            UserEntity::class,
            ChallengeEntity::class,
            UserCompletedChallengeEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun challengeDao(): ChallengeDao
}