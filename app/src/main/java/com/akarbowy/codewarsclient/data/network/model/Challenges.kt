package com.akarbowy.codewarsclient.data.network.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CompletedChallengeResponse(
        @Json(name = "totalPages") val totalPages: Int? = null,
        @Json(name = "totalItems") val totalItems: Int? = null,
        @Json(name = "data") val data: List<Challenge>? = null
)

@JsonSerializable
data class AuthoredChallengeResponse(
        @Json(name = "data") val data: List<Challenge>? = null
)

@JsonSerializable
data class Challenge(
        @Json(name = "id") val id: String? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "languages") val languages: List<String>? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "totalAttempts") val totalAttempts: Int? = 0,
        @Json(name = "totalCompleted") val totalCompleted: Int? = 0,
        @Json(name = "totalStars") val totalStars: Int? = 0,
        @Json(name = "voteScore") val voteScore: Int? = 0,
        @Json(name = "contributorsWanted") val contributorsWanted: Boolean? = null
)



