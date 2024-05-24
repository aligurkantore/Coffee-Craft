package com.example.coffeeapp.helper

import android.content.Context
import android.util.Log
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.util.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FireBaseDataManager {

    private val auth = FirebaseAuth.getInstance()
    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    var userId: String? = null
        private set

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            userId = user?.uid
            userRefCart = dataBase.getReference("users/${userId}/cart")
            userRefFavorite = dataBase.getReference("users/${userId}/favorite")
            userRefCreditCard = dataBase.getReference("users/${userId}/creditCard")
            userRefAddress = dataBase.getReference("users/${userId}/addresses")
            userRefOrderHistory = dataBase.getReference("users/${userId}/orderHistory")
        }
    }

    private lateinit var userRefCart: DatabaseReference
    private lateinit var userRefFavorite: DatabaseReference
    private lateinit var userRefCreditCard: DatabaseReference
    private lateinit var userRefAddress: DatabaseReference
    private lateinit var userRefOrderHistory: DatabaseReference

    fun addToCart(context: Context, data: CoffeeResponseModel) {
        val productId = data.id
        val productRef = userRefCart.child(productId.toString())
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showMessage(context, context.getString(R.string.product_already_in_cart))
                } else {
                    productRef.setValue(data)
                        .addOnSuccessListener {
                            BaseShared.removeKey(context, "${userId}/count_${data.id}")
                            showMessage(context, context.getString(R.string.product_added_to_cart))
                        }
                        .addOnFailureListener {
                            showMessage(context, context.getString(R.string.unexpected_error))
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "onCancelled: addToCart")
            }
        })
    }

    fun removeFromCart(context: Context, productId: String) {
        val productRef = userRefCart.child(productId)

        productRef.removeValue()
            .addOnSuccessListener {
                showMessage(context,context.getString(R.string.product_removed_from_cart))
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "error removeFromCart")
            }
    }

    fun removeAllFromCart(context: Context) {
        userRefCart.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children) {
                    productSnapshot.ref.removeValue()
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener {
                            showMessage(context, context.getString(R.string.unexpected_error))
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage(context, context.getString(R.string.unexpected_error))
            }
        })
    }

    fun toggleFavorite(context: Context, data: CoffeeResponseModel) {
        val productId = data.id

        val productRef = userRefFavorite.child(productId.toString())
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    data.isFavorite = true
                    removeFromFavorite(context, productId.toString())
                } else {
                    data.isFavorite = false
                    addToFavorite(context, data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "onCancelled: toggle favorite error")
            }
        })
    }

    fun addToFavorite(context: Context, data: CoffeeResponseModel) {
        val productId = data.id

        data.isFavorite = true
        val productRef = userRefFavorite.child(productId.toString())
        productRef.setValue(data)
            .addOnSuccessListener {
                showMessage(context, context.getString(R.string.product_added_to_favorites))
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "error addToFavorite: ")
            }
    }

    fun removeFromFavorite(context: Context, productId: String) {
        val productRef = userRefFavorite.child(productId.toString())

        productRef.removeValue()
            .addOnSuccessListener {
                showMessage(context, context.getString(R.string.product_removed_from_favorites))
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "error removeFromFavorite")
            }
    }

    fun addCreditCard(creditCard: AddCreditCard) {
        val newCreditCardRef = userRefCreditCard.push()
        newCreditCardRef.setValue(creditCard)
    }

    fun deleteCreditCardData() {
        userRefCreditCard.removeValue()
            .addOnSuccessListener {
                Log.d("agt", "Success deleting data")
            }
            .addOnFailureListener { exception ->
                Log.d("agt", "Error deleting creditCard: $exception")
            }
    }

    fun addAddress(address: AddAddress) {
        val newAddressRef = userRefAddress.push()
        newAddressRef.setValue(address)
    }

    fun deleteAddressData(data: AddAddress) {
        data.id?.let {
            val productRef = userRefAddress.child(it)
            productRef.removeValue()
                .addOnSuccessListener {
                    Log.d("agt", "Success deleting data")
                }
                .addOnFailureListener { exception ->
                    Log.d("agt", "Error deleting address: $exception")
                }
        } ?: Log.d("agt", "Error: Data ID is null")
    }

    fun moveCartToOrderHistory(context: Context) {
        userRefCart.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(cartSnapshot: DataSnapshot) {
                val coffeeList = mutableListOf<CoffeeResponseModel>()
                var orderTotal = 0.0

                for (cartItem in cartSnapshot.children) {
                    val coffee = cartItem.getValue(CoffeeResponseModel::class.java)
                    if (coffee != null) {
                        coffeeList.add(coffee)
                        orderTotal += coffee.price ?: 0.0
                    }
                }

                if (coffeeList.isNotEmpty()) {
                    val orderId = userRefOrderHistory.push().key ?: ""
                    val orderDate = System.currentTimeMillis()

                    val orderModel = OrderModel(
                        orderId = orderId,
                        orderDate = orderDate,
                        orderTotal = orderTotal,
                        coffeeList = coffeeList
                    )

                    userRefOrderHistory.child(orderId).setValue(orderModel)
                        .addOnSuccessListener {
                            userRefCart.removeValue()
                        }
                        .addOnFailureListener { error ->
                            showMessage(context, "$error")
                            Log.d("agt", "onDataChange: error: moveCartToOrderHistory")
                        }
                } else {
                    showMessage(context, context.getString(R.string.unexpected_error))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "onCancelled: moveCartToOrderHistory")
            }
        })
    }

    fun removeFromOrderHistory(context: Context, productId: String) {
        val productRef = userRefOrderHistory.child(productId)

        productRef.removeValue()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
                Log.d("agt", "removeFromOrderHistory: error")
            }
    }

}