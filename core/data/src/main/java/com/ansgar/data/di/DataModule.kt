package com.ansgar.data.di

import com.ansgar.data.currency.repository.CurrenciesDatabaseRepository
import com.ansgar.data.currency.repository.CurrenciesDatabaseRepositoryImpl
import com.ansgar.data.currency.repository.CurrenciesNetworkRepository
import com.ansgar.data.currency.repository.CurrenciesNetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    interface BindingModule {

        @Binds
        @ActivityRetainedScoped
        fun bindCurrenciesNetworkRepository(
            impl: CurrenciesNetworkRepositoryImpl
        ): CurrenciesNetworkRepository

        @Binds
        @ActivityRetainedScoped
        fun bindCurrenciesDatabaseRepository(
            impl: CurrenciesDatabaseRepositoryImpl
        ): CurrenciesDatabaseRepository
    }
}