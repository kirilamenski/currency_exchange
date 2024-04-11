package com.soapnote.database.di

import android.content.Context
import androidx.room.Room
import com.soapnote.database.dao.CurrencyDao
import com.soapnote.database.room.CURRENCIES_EX_DATABASE_NAME
import com.soapnote.database.room.CurrenciesExRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RoomModule {

    @Provides
    @ActivityRetainedScoped
    fun providesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = CurrenciesExRoom::class.java,
            name = CURRENCIES_EX_DATABASE_NAME
        ).build()

    @Provides
    @ActivityRetainedScoped
    fun providesCurrenciesDao(database: CurrenciesExRoom): CurrencyDao =
        database.currencyDao()
}