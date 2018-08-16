package com.akarbowy.codewarsclient.data.persistance.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.akarbowy.codewarsclient.data.network.model.User
import org.threeten.bp.LocalDateTime
import java.util.*

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey
        @ColumnInfo
        val userId: String,
        val position: Long?,
        val bestLanguage: String?,
        val dateCreated: LocalDateTime? = LocalDateTime.now()
) {
    object Mapper {
        fun from(data: User) =
                UserEntity(
                        data.id ?: UUID.randomUUID().toString(),
                        data.position,
                        data.bestLanguage
                )
    }
}

