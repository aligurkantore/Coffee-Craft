package com.example.coffeeapp.ui.fragments.myaddresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.repository.MyAddressesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyAddressesViewModel @Inject constructor(
    private var addressRepository: MyAddressesRepository,
) : BaseViewModel() {

    private val _addressLiveData = MutableLiveData<List<AddAddress>?>()
    val addressLiveData: LiveData<List<AddAddress>?> = _addressLiveData

    init {
        if (userId != null) {
            addressListListener(userId)
        }
    }

    private fun addressListListener(userId: String) {
        addressRepository.getAddressList(userId).observeForever {
            _addressLiveData.postValue(it)
        }
    }

}