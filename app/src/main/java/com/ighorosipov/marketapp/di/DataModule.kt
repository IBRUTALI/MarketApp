package com.ighorosipov.marketapp.di

import android.content.Context
import com.ighorosipov.marketapp.data.dto.room.MarketDao
import com.ighorosipov.marketapp.data.dto.room.MarketDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    companion object {

        @Singleton
        @Provides
        fun provideMarketDatabase(context: Context): MarketDatabase {
            return MarketDatabase.getDB(context)
        }

        @Singleton
        @Provides
        fun provideMarketDao(marketDatabase: MarketDatabase): MarketDao {
            return marketDatabase.marketDao
        }

    }

}