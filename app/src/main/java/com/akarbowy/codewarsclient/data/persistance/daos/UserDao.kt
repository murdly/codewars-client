package com.akarbowy.codewarsclient.data.persistance.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.akarbowy.codewarsclient.data.persistance.entities.UserEntity
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users ORDER BY dateCreated DESC LIMIT :count")
    fun getUsers(count: Int): Flowable<List<UserEntity>>

    @Query("SELECT * FROM users WHERE userId=:id")
    fun getUserById(id: String): Flowable<UserEntity>

}