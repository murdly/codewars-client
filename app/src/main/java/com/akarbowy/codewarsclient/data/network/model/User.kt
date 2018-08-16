package com.akarbowy.codewarsclient.data.network.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class User(
        @Json(name = "username") val id: String? = null,
        @Json(name = "leaderboardPosition") val position: Long? = null,
        @Json(name = "ranks") val ranks: Ranks? = null
) {

    var bestLanguage: String? = findBestLanguage()

    private fun findBestLanguage(): String? {
        var best: Map.Entry<String, Rank>? = null

        ranks?.let {
            it.languages?.let { language ->
                best = language.maxBy { it.value.score ?: 0 }
            }
        }

        return best?.let {
            "${it.key}, ${it.value.score}"
        }
    }
}

@JsonSerializable
data class Ranks(
        @Json(name = "overall") val rank: Rank? = null,
        @Json(name = "languages") val languages: Map<String, Rank>? = null
)

@JsonSerializable
data class Rank(
        @Json(name = "rank") val rank: Long? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "color") val color: String? = null,
        @Json(name = "score") val score: Long? = null
)
