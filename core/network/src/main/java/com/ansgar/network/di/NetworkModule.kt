package com.ansgar.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val API_KEY = "9fuQMyeye6uysZUXji862ypclzVCx1YA"
private const val BASE_URL = "https://api.currencybeacon.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    // Using callFactory lambda here with dagger.Lazy<Call.Factory>
    // to prevent initializing OkHttp on the main thread.
    @Provides
//    @Singleton
    fun providesRetrofit(okhttpCallFactory: dagger.Lazy<Call.Factory>) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
//    @Singleton
    fun providesOkhttpClient(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()

                chain.proceed(request)
            }
        )
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        )
        .build()
}