package com.akarbowy.codewarsclient.network.injection

import com.akarbowy.codewarsclient.injection.scopes.PerApplication
import com.akarbowy.codewarsclient.network.adapter.ApplicationJsonAdapterFactory
import com.akarbowy.codewarsclient.network.api.CodewarsApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    @PerApplication
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @PerApplication
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val authorizedRequest = request
                    .newBuilder()
                    .header("Authorization", CodewarsApi.API_KEY)
                    .build()
            chain.proceed(authorizedRequest)
        }
    }

    @Provides
    @PerApplication
    fun provideMoshi(): Moshi = Moshi.Builder().add(ApplicationJsonAdapterFactory.INSTANCE).build()

    @Provides
    @PerApplication
    fun provideConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @PerApplication
    fun provideApi(builder: Retrofit.Builder,
                          okHttpClientBuilder: OkHttpClient.Builder,
                          converterFactory: Converter.Factory,
                          apiKeyInterceptor: Interceptor): CodewarsApi {

        okHttpClientBuilder.addNetworkInterceptor(apiKeyInterceptor)
        val client = okHttpClientBuilder.build()
        return builder.client(client)
                .baseUrl(CodewarsApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()
                .create(CodewarsApi::class.java)
    }
}
