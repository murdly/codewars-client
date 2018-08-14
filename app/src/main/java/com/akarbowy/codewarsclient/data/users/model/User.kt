package com.akarbowy.codewarsclient.data.users.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class User(
        @Json(name = "username") val id: String? = null,
        @Json(name = "leaderboardPosition") val position: Long? = null,
        @Json(name = "ranks") val ranks: Ranks? = null
)

@JsonSerializable
data class Ranks(
        @Json(name = "overall") val rank: Rank? = null,
        @Json(name = "languages") val name: Language? = null
)

@JsonSerializable
data class Rank(
        @Json(name = "rank") val rank: Long? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "color") val color: String? = null,
        @Json(name = "score") val score: Long? = null
)

@JsonSerializable
data class Language(
        @Json(name = "java") val rank: Rank? = null
)
