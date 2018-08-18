package com.akarbowy.codewarsclient.data.persistance.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.akarbowy.codewarsclient.data.persistance.entities.ChallengeEntity
import com.akarbowy.codewarsclient.data.persistance.entities.UserCompletedChallengeEntity


@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChallenge(challenges: List<ChallengeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCompletedChallenge(userChallenge: UserCompletedChallengeEntity)

    @Query("""
        SELECT * FROM challenges
        INNER JOIN user_completed_challenges ON
        challenges.challengeId=challengeJoinId WHERE
        user_completed_challenges.userJoinId=:username
        """)
    fun getUserCompletedChallenges(username: String): DataSource.Factory<Int, ChallengeEntity>

    @Query("SELECT COUNT(*) FROM challenges")
    fun count(): Int

}