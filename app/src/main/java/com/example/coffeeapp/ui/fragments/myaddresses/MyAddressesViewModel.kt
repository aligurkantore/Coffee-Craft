package com.example.coffeeapp.ui.fragments.myaddresses

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.address.AddAddress
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class MyAddressesViewModel : BaseViewModel() {

    private val databaseRef: DatabaseReference = database.getReference("users/$userId/addresses")

    private val _addressLiveData = MutableLiveData<List<AddAddress>?>()
    val addressLiveData: LiveData<List<AddAddress>?> = _addressLiveData

    init {
        getAddress()
    }

    private fun getAddress() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val addressList = mutableListOf<AddAddress>()
                for (snapShot in dataSnapshot.children) {
                    val value = snapShot.getValue(AddAddress::class.java)?.copy(id = snapShot.key)
                    value?.let {
                        addressList.add(it)
                    }
                }
                _addressLiveData.postValue(addressList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("agt", "onCancelled: database error")
            }
        })
    }
}