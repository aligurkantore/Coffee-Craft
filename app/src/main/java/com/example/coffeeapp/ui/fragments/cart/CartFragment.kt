package com.example.coffeeapp.ui.fragments.cart

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentCartBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.cart.CartAdapter
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private var cartAdapter: CartAdapter? = null
    private var cartItems: MutableList<CoffeeResponseModel> = mutableListOf()
    private var recyclerViewState: Parcelable? = null

    override val viewModelClass: Class<out CartViewModel>
        get() = CartViewModel::class.java

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    override fun setUpListeners() {
        viewBindingScope {
            buttonPayCart.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_myAddressesFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModelScope {
            cartItemsLiveData.observeNonNull(viewLifecycleOwner) { list ->
                if (viewModel.isLoggedIn()) {
                    if (list.isEmpty()) {
                        setUIView(false)
                    } else {
                        setUIView(true)
                        setUpCartAdapter(list)
                        cartItems.clear()
                        cartItems.addAll(list)
                        updateTotalPrice()
                    }
                } else clearCart()

            }
            authStateLiveData.observeNonNull(viewLifecycleOwner) { isLoggedIn ->
                if (!isLoggedIn) {
                    clearCart()
                    isUserLoggedIn(false)
                }
            }

        }
    }


    private fun setUpCartAdapter(data: List<CoffeeResponseModel>) {
        cartAdapter = CartAdapter(
            mContext,
            data.toMutableList(),
            ::navigateToDetail,
            object : CartAdapter.TotalPriceListener {
                override fun onTotalPriceUpdated(totalPrice: String, count: Int) {
                    updateTotalPrice()
                }
            },
            ::showDeleteItemCart
        )
        binding?.recyclerCart?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
        if (recyclerViewState != null) {
            view?.post {
                binding?.recyclerCart?.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        }
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


    private fun setUIView(isVisible: Boolean) {
        viewBindingScope {
            recyclerCart visibleIf isVisible
            textTitlePrice visibleIf isVisible
            textPrice visibleIf isVisible
            buttonPayCart visibleIf isVisible
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.coffee)
                textEmpty.text = getString(R.string.empty_cart)
                buttonAction.text = getString(R.string.start_shopping)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isVisible
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_cartFragment_to_homeFragment)
                    }
                }
            }
        }
    }


    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            recyclerCart visibleIf isLogin
            textTitlePrice visibleIf isLogin
            textPrice visibleIf isLogin
            buttonPayCart visibleIf isLogin
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.coffee)
                textEmpty.text = getString(R.string.must_login)
                buttonAction.text = getString(R.string.login)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isLogin
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_cartFragment_to_loginFragment)
                    }
                }
            }
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

    private fun clearCart() {
        cartItems.clear()
        cartAdapter?.notifyDataSetChanged()
        setUIView(false)
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumByDouble {
            val count =
                BaseShared.getInt(mContext, "${viewModel.userId}/count_${it.id}", 1)
            (it.price)?.times(count) ?: 0.0
        }
        val formattedPrice = mContext.getString(R.string.price_format, totalPrice)
        binding?.textPrice?.text = formattedPrice
        BaseShared.saveString(mContext, "totalPrice", formattedPrice)
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding?.recyclerCart?.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            binding?.recyclerCart?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }
    }

}