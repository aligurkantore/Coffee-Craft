package com.example.coffeeapp.ui.fragments.paymentinformation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class PaymentInformationViewModel : BaseViewModel() {

    private val databaseRef: DatabaseReference = database.getReference("users/$userId/creditCard")

    private val _creditCardLiveData = MutableLiveData<AddCreditCard?>()
    val creditCardLiveData: LiveData<AddCreditCard?> = _creditCardLiveData

    init {
        getCreditCard()
    }

    fun getCreditCard() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapShot in dataSnapshot.children) {
                    val value = snapShot.getValue(AddCreditCard::class.java)
                    _creditCardLiveData.postValue(value)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("agt", "onCancelled: database error")
            }
        })
    }
}