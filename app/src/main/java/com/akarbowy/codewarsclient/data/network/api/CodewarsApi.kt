package com.akarbowy.codewarsclient.data.network.api

import com.akarbowy.codewarsclient.data.network.model.AuthoredChallengeResponse
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.network.model.CompletedChallengeResponse
import com.akarbowy.codewarsclient.data.network.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsApi {

    @GET("users/{id}")
    fun getUser(@Path("id") username: String): Single<User>

    @GET("users/{id}/code-challenges/completed")
    fun getCompletedChallenges(@Path("id") username: String,
                               @Query("page") page: Int): Single<CompletedChallengeResponse>

    @GET("users/{id}/code-challenges/authored")
    fun getAuthoredChallenges(@Path("id") username: String): Single<AuthoredChallengeResponse>

    @GET("code-challenges/{id}")
    fun getChallengeDetail(@Path("id") username: String): Single<Challenge>

    companion object {
        const val BASE_URL = "https://www.codewars.com/api/v1/"
        //todo secure it, firebase or smth
        const val API_KEY = "hfvo1yYa5rKcMNhdrnjb"
    }

}
