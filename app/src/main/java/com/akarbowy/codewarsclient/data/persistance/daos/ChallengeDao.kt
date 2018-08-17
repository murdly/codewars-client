package com.akarbowy.codewarsclient.data.persistance.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity



@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChallenge(challenges : List<ChallengeEntity>)

    @Query("SELECT * FROM challenges")
    fun getChallenges() : DataSource.Factory<Int, ChallengeEntity>

    @Query("SELECT COUNT(*) FROM challenges")
    fun count(): Int

}