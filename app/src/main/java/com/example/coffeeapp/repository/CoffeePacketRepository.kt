package com.example.coffeeapp.repository

import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import com.example.coffeeapp.network.Service
import retrofit2.Response
import javax.inject.Inject

class CoffeePacketRepository @Inject constructor(private val service: Service) {

    suspend fun getCoffee(): Response<CoffeePacketResponse> {
        return service.getCoffee()
    }
}