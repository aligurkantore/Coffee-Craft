package com.example.coffeeapp.usecase

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.repository.PaymentInformationRepository
import javax.inject.Inject

class PaymentUseCase @Inject constructor(
    private val paymentInformationRepository: PaymentInformationRepository
) {
    fun getCreditCard(userId: String): LiveData<AddCreditCard?>{
        return paymentInformationRepository.getCreditCard(userId)
    }
}