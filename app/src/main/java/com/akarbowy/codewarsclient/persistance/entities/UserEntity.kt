package com.akarbowy.codewarsclient.persistance.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import com.akarbowy.codewarsclient.data.users.model.User
import org.threeten.bp.LocalDateTime
import java.util.*

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey
        @ColumnInfo
        val userId: String,
        val dateCreated: LocalDateTime? = LocalDateTime.now()
) {
    object Mapper {
        fun from(data: User) =
                UserEntity(
                        data.id ?: UUID.randomUUID().toString()
                )
    }
}

