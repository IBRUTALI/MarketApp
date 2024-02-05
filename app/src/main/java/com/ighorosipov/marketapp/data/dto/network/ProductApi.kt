package com.ighorosipov.marketapp.data.dto.network

import com.ighorosipov.marketapp.data.model.Items
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProducts(): Response<Items>

    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }
}