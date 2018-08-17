package com.akarbowy.codewarsclient.data.network.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CompletedChallengeResponse(
        @Json(name = "totalPages") val totalPages: Int? = null,
        @Json(name = "totalItems") val totalItems: Int? = null,
        @Json(name = "data") val data: List<Challenge>? = null
)

//@JsonSerializable
//data class CompletedChallenge(
//        @Json(name = "id") val id: String? = null,
//        @Json(name = "name") val name: String? = null,
//        @Json(name = "slug") val slug: String? = null,
//        @Json(name = "completedLanguages") val completedLanguages: List<String>? = null,
//        @Json(name = "completedAt") val completedAt: String? = null
//)

@JsonSerializable
data class AuthoredChallengeResponse(
        @Json(name = "data") val data: List<Challenge>? = null
)

//@JsonSerializable
//data class AuthoredChallenge(
//        @Json(name = "id") val id: String? = null,
//        @Json(name = "name") val name: String? = null,
//        @Json(name = "description") val description: String? = null,
//        @Json(name = "rank") val rank: Int? = null,
//        @Json(name = "rankName") val rankName: String? = null,
//        @Json(name = "tags") val tags: List<String>? = null,
//        @Json(name = "languages") val languages: List<String>? = null
//)

@JsonSerializable
data class Challenge(
        @Json(name = "id") val id: String? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "slug") val slug: String? = null,
        @Json(name = "category") val category: String? = null,
        @Json(name = "publishedAt") val publishedAt: String? = null,
        @Json(name = "approvedAt") val approvedAt: String? = null,
        @Json(name = "languages") val languages: List<String>? = null,
        @Json(name = "url") val url: String? = null,
//        @Json(name = "rank") val rank: Rank? = null,
        @Json(name = "createdAt") val createdAt: String? = null,
//        @Json(name = "createdBy") val createdBy: ChallengeAuthor? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "tags") val tags: List<String>? = null,
        @Json(name = "totalAttempts") val totalAttempts: Int? = null,
        @Json(name = "totalCompleted") val totalCompleted: Int? = null,
        @Json(name = "totalStars") val totalStars: Int? = null,
        @Json(name = "voteScore") val voteScore: Int? = null
)

//@JsonSerializable
//data class ChallengeAuthor(
//        @Json(name = "username") val username: String? = null,
//        @Json(name = "url") val url: String? = null
//)


