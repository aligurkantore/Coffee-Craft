package com.example.coffeeapp.helper

import android.content.Context
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponseItem
import com.example.coffeeapp.util.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FireBaseDataManager {

    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userId = auth.currentUser?.uid
    private val userRefCart: DatabaseReference = dataBase.getReference("users/$userId/cart")
    private val userRefFavorite: DatabaseReference = dataBase.getReference("users/$userId/favorite")
    private val userRefOrderHistory: DatabaseReference = dataBase.getReference("users/$userId/orderHistory")

    fun addToCart(context: Context, data: Any) {
        val productId = when (data) {
            is CoffeeResponseModel -> data.id
            is CoffeePacketResponseItem -> data.id.toString()
            else -> null
        }

        productId?.let { id ->
            val productRef = userRefCart.child(id)
            productRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        showMessage(context, context.getString(R.string.product_already_in_cart))
                    } else {
                        productRef.setValue(data)
                            .addOnSuccessListener {
                                BaseShared.removeKey(context, "count_${id}")
                                showMessage(context, context.getString(R.string.product_added_to_cart))
                            }
                            .addOnFailureListener {
                                showMessage(context, context.getString(R.string.unexpected_error))
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })
        }
    }



    fun addToFavorite(context: Context, data: Any) {
        val productId = when (data) {
            is CoffeeResponseModel -> data.id
            is CoffeePacketResponseItem -> data.id.toString()
            else -> null
        }

        productId?.let { id ->
            val productRef = userRefFavorite.child(id)
            productRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        showMessage(context, context.getString(R.string.product_already_in_favorites))
                    } else {
                        productRef.setValue(data)
                            .addOnSuccessListener {
                                BaseShared.removeKey(context, "count_${id}")
                                showMessage(context, context.getString(R.string.product_added_to_favorites))
                            }
                            .addOnFailureListener {
                                showMessage(context, context.getString(R.string.unexpected_error))
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })
        }
    }

    fun addToOrderHistory(context: Context, data: CoffeeResponseModel) {
        val productId = data.id

        val productRef = userRefOrderHistory.child(productId.toString())
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showMessage(context, context.getString(R.string.product_already_in_favorites))
                } else {
                    productRef.setValue(data)
                        .addOnSuccessListener {
                            showMessage(context, context.getString(R.string.product_added_to_favorites))
                        }
                        .addOnFailureListener {
                            showMessage(context, context.getString(R.string.unexpected_error))
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

    fun removeFromCart(context: Context, productId: String) {
        val productRef = userRefCart.child(productId)

        productRef.removeValue()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
            }
    }

    fun removeFromFavorite(context: Context, productId: String) {
        val productRef = userRefFavorite.child(productId)

        productRef.removeValue()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                showMessage(context, context.getString(R.string.unexpected_error))
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

    fun removeAllFromFavorite(context: Context) {
        userRefFavorite.addListenerForSingleValueEvent(object : ValueEventListener {
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

}