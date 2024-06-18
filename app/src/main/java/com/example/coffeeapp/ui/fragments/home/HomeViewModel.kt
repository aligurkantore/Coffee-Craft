package com.example.coffeeapp.ui.fragments.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponse
import com.example.coffeeapp.usecase.home.CoffeePacketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var coffeePacketUseCase: CoffeePacketUseCase
) : BaseViewModel() {

    private val _packetFlow = MutableStateFlow<CoffeePacketResponse?>(null)
    val packetFlow: StateFlow<CoffeePacketResponse?> = _packetFlow.asStateFlow()


    init {
        getCoffeePacket()
    }

    private fun getCoffeePacket(){
        viewModelScope.launch {
          //  setLoading(true)
            try {
                coffeePacketUseCase.getCoffeePacket().collect { response ->
                    _packetFlow.value = response
                }
            } catch (e: Exception) {
                Log.d("agt", "getCoffeePacket: ${e.message}")
            }finally {
            //    setLoading(false)
            }
        }
    }

}