package com.akarbowy.codewarsclient.data.persistance.converters

import android.arch.persistence.room.TypeConverter
import java.util.*


class Converters {

//    @TypeConverter
//    fun fromTimestamp(value: Long): LocalDateTime {
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: LocalDateTime): Long {
//        return date.toInstant(ZonedDateTime.now(ZoneOffset.UTC).offset).toEpochMilli()
//    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return value?.time
    }
}
