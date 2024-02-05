package com.ighorosipov.marketapp.data.repository

import com.ighorosipov.marketapp.data.dto.network.ProductApi
import com.ighorosipov.marketapp.data.mapper.ProductMapper
import com.ighorosipov.marketapp.domain.model.Items
import com.ighorosipov.marketapp.domain.repository.ProductRepository
import com.ighorosipov.marketapp.domain.utils.Result
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
): ProductRepository {

    override suspend fun getProducts(): Result<Items> {
        return try {
            Result.Success(
                data = ProductMapper().mapItemsToDomain(api.getProducts().body()!!)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = e.message ?: "An unknown error occurred.")
        }
    }

}