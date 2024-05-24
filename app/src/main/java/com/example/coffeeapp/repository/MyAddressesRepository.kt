package com.example.coffeeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.models.address.AddAddress
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class MyAddressesRepository @Inject constructor(
    private val databaseReference: DatabaseReference
) {

    fun getAddressList(userId: String): LiveData<List<AddAddress>> {
        val addressListLiveData = MutableLiveData<List<AddAddress>>()
        val userRef = databaseReference.child("users").child(userId).child("addresses")
        
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val addressList = mutableListOf<AddAddress>()
                for (snapShot in dataSnapshot.children){
                    val value = snapShot.getValue(AddAddress::class.java)?.copy(id = snapShot.key)
                    value?.let { addressList.add(it) }
                }
                addressListLiveData.postValue(addressList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: myAddressesRepository")
            }

        })
        return addressListLiveData
    }
}