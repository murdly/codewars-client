package com.akarbowy.codewarsclient.persistance.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.akarbowy.codewarsclient.persistance.entities.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(note: UserEntity): Long

    @Query("SELECT * FROM users ORDER BY dateCreated")
    fun getUsers(): Flowable<List<UserEntity>>

    @Query("SELECT * FROM users WHERE userId=:id")
    fun getUserById(id: String): Single<UserEntity>

}