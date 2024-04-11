package com.ansgar.network.di

import com.ansgar.network.api.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object ApiModule {

    @Provides
    @ActivityRetainedScoped
    fun providesCurrencyApi(retrofit: Retrofit) =
        retrofit.create(CurrencyApi::class.java)
}