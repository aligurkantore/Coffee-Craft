package com.example.coffeeapp.ui.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentCartBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.cart.CartAdapter
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.NavigationManager
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.visibleIf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private lateinit var cartAdapter: CartAdapter
    private val cartItems: MutableList<CoffeeResponseModel> = mutableListOf()
    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override val viewModelClass: Class<out CartViewModel>
        get() = CartViewModel::class.java

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isLoggedIn()) {
            isUserLoggedIn(true)
            setUpCartAdapter()
            getProductsFromDataBase()
            removeItemsInCart()
        } else {
            isUserLoggedIn(false)
        }
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonPayCart.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_paymentInformationFragment)
            }

            buttonLogin.setOnClickListener {
                NavigationManager.apply {
                    setCurrentFragmentId(R.id.cartFragment)
                    navigateToLogin(findNavController())
                }
            }
        }
    }

    override fun setUpObservers() {
    }

    private fun getProductsFromDataBase() {
        val userId = Firebase.auth.currentUser?.uid
        val userRef: DatabaseReference = dataBase.getReference("users/$userId/cart")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    if (item != null) {
                        tempList.add(item)
                    }
                }
                cartItems.clear()
                cartItems.addAll(tempList)
                BaseShared.saveCartItems(mContext, cartItems)
             //   Log.d("agt", "removeItemsInCart: $cartItems")
                updateTotalPrice()
                checkItemsInAdapter()
                cartAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled ")
            }
        })
    }

    private fun setUpCartAdapter() {
        cartAdapter = CartAdapter(
            mContext,
            cartItems,
            ::navigateToDetail,
            object : CartAdapter.TotalPriceListener {
                override fun onTotalPriceUpdated(totalPrice: String, count: Int) {
                    binding?.textPrice?.text = totalPrice
                }
            },
            ::showDeleteItemCart
        )
        binding?.recyclerCart?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumByDouble {
            val count = BaseShared.getInt(mContext, "count_${it.id}", 1)
            //  val price1 = BaseShared.getString(mContext, "size_${it.id}", "")
            (it.prices?.first()?.price)?.times(count) ?: 0.0
        }
        binding?.textPrice?.text = totalPrice.toString()
        BaseShared.saveString(mContext, "totalPrice", totalPrice.toString())
    }

    private fun navigateToDetail(data: CoffeeResponseModel) {
        val bundle = Bundle().apply {
            putSerializable(DETAIL, data)
        }
        navigateSafeWithArgs(R.id.action_cartFragment_to_detailFragment, bundle)
    }

    private fun deleteItemInAdapter(data: CoffeeResponseModel) {
        data.id?.let { FireBaseDataManager.removeFromCart(mContext, it) }
    }


    private fun checkItemsInAdapter() {
        val isEmpty = cartItems.isEmpty()
        binding?.apply {
            recyclerCart.visibility = if (isEmpty) View.GONE else View.VISIBLE
            textTitlePrice.visibility = if (isEmpty) View.GONE else View.VISIBLE
            textPrice.visibility = if (isEmpty) View.GONE else View.VISIBLE
            buttonPayCart.visibility = if (isEmpty) View.GONE else View.VISIBLE
            imageEmptyCart.visibility = if (isEmpty) View.VISIBLE else View.GONE
            textEmptyCart.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        binding?.apply {
            imageEmptyCart goneIf isLogin
            textNotLoggedIn goneIf isLogin
            buttonLogin goneIf isLogin
            recyclerCart visibleIf isLogin
            textTitlePrice visibleIf isLogin
            textPrice visibleIf isLogin
            buttonPayCart visibleIf isLogin
        }
    }

    private fun showDeleteItemCart(data: CoffeeResponseModel) {
        CustomDialog(
            mContext,
            message = getString(R.string.confirm_delete_product),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            positiveButtonClickListener = {
                deleteItemInAdapter(data)
            }
        ).show()
    }

    private fun removeItemsInCart() {
        val successOrder = BaseShared.getString(mContext, "successOrder", "")
        if (successOrder == "successOrder") {
            FireBaseDataManager.removeAllFromCart(mContext)
            BaseShared.removeKey(mContext, "successOrder")
        } else {
          //  Log.d("agt", "removeItemsInCart: There was a problem with the order")
        }
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.my_cart))
    }

}