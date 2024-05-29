package com.example.coffeeapp.network

import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("api")
    suspend fun getCoffee() : Response<CoffeePacketResponse>
}