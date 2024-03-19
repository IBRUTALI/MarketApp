package com.ighorosipov.marketapp.domain.repository

import com.ighorosipov.marketapp.data.model.Items
import com.ighorosipov.marketapp.domain.utils.Result

interface ProductRepository {

    suspend fun getProducts(): Result<Items>

}