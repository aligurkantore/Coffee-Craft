package com.example.coffeeapp.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class CartViewModel: BaseViewModel() {

    private val userRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users/${Firebase.auth.currentUser?.uid}/cart")
    private val cartItems: MutableLiveData<List<CoffeeResponseModel>> = MutableLiveData()
    private var totalPrice: MutableLiveData<Double> = MutableLiveData()


    fun getProductFromDB(){
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<CoffeeResponseModel> = mutableListOf()
                var total: Double = 0.0
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    if (item != null) {
                        tempList.add(item)
                        total += item.prices?.first()?.price!!
                    }
                }
                cartItems.value = tempList
                totalPrice = getTotalPrice()
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacaklar
            }
        })
    }

    fun getCartItems(): LiveData<List<CoffeeResponseModel>> {
        return cartItems
    }

    fun getTotalPrice(): MutableLiveData<Double> {
        val totalPrice = MutableLiveData<Double>()
        val calculatedPrice = cartItems.value?.sumByDouble {
            (it.prices?.first()?.price ?: 0.0) * (it.count ?: 0)
        }
        totalPrice.value = calculatedPrice ?: 0.0
        return totalPrice
    }

    // ViewModel'de gerektiği yerde diğer işlemler de eklenebilir
    // Örneğin, ürünü sepetten silme gibi
}