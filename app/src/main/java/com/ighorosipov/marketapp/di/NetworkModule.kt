package com.ighorosipov.marketapp.di

import com.ighorosipov.marketapp.data.dto.network.ProductApi
import com.ighorosipov.marketapp.data.dto.network.ProductApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
interface NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideMarketService(): ProductApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }

    }

}