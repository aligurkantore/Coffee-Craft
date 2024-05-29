package com.example.coffeeapp.usecase

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.repository.MyAddressesRepository
import javax.inject.Inject

class MyAddressesUseCase @Inject constructor(
    private var myAddressesRepository: MyAddressesRepository
) {
    fun getAddressList(userId: String): LiveData<List<AddAddress>>{
        return myAddressesRepository.getAddressList(userId)
    }
}