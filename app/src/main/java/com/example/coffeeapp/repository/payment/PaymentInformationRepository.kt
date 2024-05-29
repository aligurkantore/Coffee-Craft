package com.example.coffeeapp.repository.payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class PaymentInformationRepository @Inject constructor(
    private val databaseReference: DatabaseReference
) {

    fun getCreditCard(userId: String): LiveData<AddCreditCard?>{
        val creditCardLiveData = MutableLiveData<AddCreditCard?>()
        val userRef = databaseReference.child("users").child(userId).child("creditCard")
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapShot in snapshot.children) {
                    val value = snapShot.getValue(AddCreditCard::class.java)
                    creditCardLiveData.postValue(value)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: paymentInformationRepository")
            }

        })
        return creditCardLiveData
    }
}