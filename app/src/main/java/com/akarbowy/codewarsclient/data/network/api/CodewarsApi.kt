package com.akarbowy.codewarsclient.data.network.api

import com.akarbowy.codewarsclient.data.network.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CodewarsApi {

    @GET("users/{id}")
    fun getUser(@Path("id") username: String) : Single<User>

    companion object {
        const val BASE_URL = "https://www.codewars.com/api/v1/"
        //todo secure it, firebase or smth
        const val API_KEY = "hfvo1yYa5rKcMNhdrnjb"
    }

}
