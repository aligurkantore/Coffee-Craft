package com.example.coffeeapp.ui.fragments.paymentinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.usecase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentInformationViewModel @Inject constructor(
    private val paymentUseCase: PaymentUseCase
) : BaseViewModel() {

    private val _creditCardLiveData = MutableLiveData<AddCreditCard?>()
    val creditCardLiveData: LiveData<AddCreditCard?> = _creditCardLiveData

    init {
        userId?.let { creditCardListener(it) }
    }

    fun creditCardListener(userId: String) {
        paymentUseCase.getCreditCard(userId).observeForever {
            _creditCardLiveData.postValue(it)
        }
    }
}