package com.example.coffeeapp.repository.home

import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import com.example.coffeeapp.network.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoffeePacketRepository @Inject constructor(private val service: Service) {

    suspend fun getCoffeePacket(): Flow<CoffeePacketResponse?> {
        return flow {
            emit(service.getCoffee().body())
        }
    }
}