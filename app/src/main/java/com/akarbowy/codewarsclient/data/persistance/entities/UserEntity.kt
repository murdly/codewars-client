package com.akarbowy.codewarsclient.data.persistance.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.akarbowy.codewarsclient.data.network.model.User
import java.util.*

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey
        @ColumnInfo
        val userId: String,
        val position: Long? = null,
        val bestLanguage: String? = null,
        val dateCreated: Date? = Date()
)

open class UserMapper {

    open fun from(data: User): UserEntity {
        return UserEntity(
                data.id ?: UUID.randomUUID().toString(),
                data.position,
                data.bestLanguage
        )
    }

}