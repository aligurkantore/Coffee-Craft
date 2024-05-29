package com.example.coffeeapp.usecase.payment

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.repository.payment.PaymentInformationRepository
import javax.inject.Inject

class PaymentUseCase @Inject constructor(
    private val paymentInformationRepository: PaymentInformationRepository
) {
    fun getCreditCard(userId: String): LiveData<AddCreditCard?>{
        return paymentInformationRepository.getCreditCard(userId)
    }
}