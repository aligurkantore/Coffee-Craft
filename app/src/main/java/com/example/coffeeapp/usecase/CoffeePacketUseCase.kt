package com.example.coffeeapp.usecase

import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import com.example.coffeeapp.repository.CoffeePacketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoffeePacketUseCase @Inject constructor(
    private val coffeePacketRepository: CoffeePacketRepository
) {
    suspend fun getCoffeePacket(): Flow<CoffeePacketResponse?> {
        return coffeePacketRepository.getCoffeePacket()
    }
}