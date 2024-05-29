package com.example.coffeeapp.ui.fragments.myaddresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.usecase.MyAddressesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyAddressesViewModel @Inject constructor(
    private var myAddressesUseCase: MyAddressesUseCase,
) : BaseViewModel() {

    private val _addressLiveData = MutableLiveData<List<AddAddress>?>()
    val addressLiveData: LiveData<List<AddAddress>?> = _addressLiveData

    init {
        userId?.let { addressListListener(it) }
    }

    private fun addressListListener(userId: String) {
        myAddressesUseCase.getAddressList(userId).observeForever {
            _addressLiveData.postValue(it)
        }
    }

}