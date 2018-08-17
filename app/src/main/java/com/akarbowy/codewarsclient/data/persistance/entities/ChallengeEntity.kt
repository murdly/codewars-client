package com.akarbowy.codewarsclient.data.persistance.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.akarbowy.codewarsclient.data.network.model.Challenge
import java.util.*

@Entity(tableName = "challenges")
data class ChallengeEntity(
        @PrimaryKey
        @ColumnInfo
        val challengeId: String,
        val name: String?
) {
    object Mapper {
        fun from(data: Challenge) =
                ChallengeEntity(
                        data.id ?: UUID.randomUUID().toString(),
                        data.name
                )
    }
}

