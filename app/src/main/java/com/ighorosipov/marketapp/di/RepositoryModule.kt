package com.ighorosipov.marketapp.di

import com.ighorosipov.marketapp.data.repository.MarketRepositoryImpl
import com.ighorosipov.marketapp.data.repository.ProductRepositoryImpl
import com.ighorosipov.marketapp.domain.repository.MarketRepository
import com.ighorosipov.marketapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindProductRepository(productRepository: ProductRepositoryImpl): ProductRepository

    @Singleton
    @Binds
    fun bindMarketRepository(marketRepository: MarketRepositoryImpl): MarketRepository

}