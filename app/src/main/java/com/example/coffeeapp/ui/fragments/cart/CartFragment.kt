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
import com.example.coffeeapp.util.ProgressBarUtil
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visibleIf


class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private var cartAdapter: CartAdapter? = null
    private var cartItems: MutableList<CoffeeResponseModel> = mutableListOf()
    private lateinit var progressBarUtil: ProgressBarUtil

    override val viewModelClass: Class<out CartViewModel>
        get() = CartViewModel::class.java

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarUtil = ProgressBarUtil(mContext, binding?.root as ViewGroup)
        viewModel.startAuthStateListener()
        isUserLoggedIn(viewModel.isLoggedIn())
        setUIView(false)

        //  progressBarUtil.showProgressBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonPayCart.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_myAddressesFragment)
            }
            buttonStartShopping.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_homeFragment)
            }

            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_cartFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.apply {
            cartItemsLiveData.observeNonNull(viewLifecycleOwner) { list ->
                if (viewModel.isLoggedIn()) {
                    if (list.isEmpty()) {
                        progressBarUtil.hideProgressBar()
                        setUIView(false)
                    } else {
                        setUIView(true)
                        setUpCartAdapter(list)
                        cartItems.clear()
                        cartItems.addAll(list)

                        val totalPrice = cartItems.sumByDouble {
                            val count =
                                BaseShared.getInt(mContext, "${viewModel.userId}/count_${it.id}", 1)
                            (it.price)?.times(count) ?: 0.0
                        }
                        val formattedPrice =
                            mContext.getString(R.string.price_format, totalPrice)
                        binding?.textPrice?.text = formattedPrice
                        BaseShared.saveString(mContext, "totalPrice", totalPrice.toString())

                        //   progressBarUtil.hideProgressBar()
                    }
                } else clearCart()

                cartAdapter?.notifyDataSetChanged()
            }
            authStateLiveData.observeNonNull(viewLifecycleOwner) { isLoggedIn ->
                isUserLoggedIn(isLoggedIn)
                if (!isLoggedIn) {
                    clearCart()
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
                    val formattedPrice =
                        mContext.getString(R.string.price_format, totalPrice.toDouble())
                    binding?.textPrice?.text = formattedPrice
                }
            },
            ::showDeleteItemCart
        )
        binding?.recyclerCart?.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(mContext)
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
        binding?.apply {
            recyclerCart visibleIf isVisible
            textTitlePrice visibleIf isVisible
            textPrice visibleIf isVisible
            buttonPayCart visibleIf isVisible
            imageEmptyCart goneIf isVisible
            textEmptyCart goneIf isVisible
            buttonStartShopping goneIf isVisible
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

    private fun clearCart() {
        cartItems.clear()
        cartAdapter?.notifyDataSetChanged()
        setUIView(false)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopAuthStateListener()
    }

}