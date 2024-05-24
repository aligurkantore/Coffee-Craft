package com.example.coffeeapp.ui.fragments.paymentinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.repository.PaymentInformationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentInformationViewModel @Inject constructor(
    private val paymentRepository: PaymentInformationRepository
) : BaseViewModel() {

    private val _creditCardLiveData = MutableLiveData<AddCreditCard?>()
    val creditCardLiveData: LiveData<AddCreditCard?> = _creditCardLiveData

    init {
        if (userId != null) {
            creditCardListener(userId)
        }
    }

    fun creditCardListener(userId: String) {
        paymentRepository.getCreditCard(userId).observeForever {
            _creditCardLiveData.postValue(it)
        }
    }
}