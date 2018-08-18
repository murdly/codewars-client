package com.akarbowy.codewarsclient.data.persistance.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(
        tableName = "user_authored_challenges",
        indices = [Index("userJoinId", "challengeJoinId")],
        primaryKeys = ["userJoinId", "challengeJoinId"],
        foreignKeys = [
            ForeignKey(entity = UserEntity::class,
                    parentColumns = ["userId"],
                    childColumns = ["userJoinId"]),
            ForeignKey(entity = ChallengeEntity::class,
                    parentColumns = ["challengeId"],
                    childColumns = ["challengeJoinId"])
        ]
)
data class UserAuthoredChallengeEntity(
        val userJoinId: String,
        val challengeJoinId: String
)