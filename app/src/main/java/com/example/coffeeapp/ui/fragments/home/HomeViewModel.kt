package com.example.coffeeapp.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import com.example.coffeeapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var repository: Repository) : BaseViewModel() {

    private val _packetLiveData = MutableLiveData<CoffeePacketResponse>()
    val packetLiveData: LiveData<CoffeePacketResponse> = _packetLiveData


    init {
        getCoffeePacket()
    }

    private fun getCoffeePacket(){
        performRequest(_packetLiveData){
            repository.getCoffee()
        }
    }

}