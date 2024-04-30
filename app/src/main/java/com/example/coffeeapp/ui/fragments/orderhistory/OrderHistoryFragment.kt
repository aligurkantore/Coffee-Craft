package com.example.coffeeapp.ui.fragments.orderhistory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentOrderHistoryBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.orderhistory.OrderHistoryAdapter
import com.example.coffeeapp.util.ObjectUtil

class OrderHistoryFragment : BaseFragment<FragmentOrderHistoryBinding, OrderHistoryViewModel>() {

    private lateinit var orderHistoryAdapter: OrderHistoryAdapter

    override val viewModelClass: Class<out OrderHistoryViewModel>
        get() = OrderHistoryViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOrderHistoryBinding {
        return FragmentOrderHistoryBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
        setUpOrderHistoryAdapter()
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }

    private fun setUpOrderHistoryAdapter() {
        val orderHistoryData = BaseShared.getCartItems(mContext)
        Log.d("agt", "setUpListeners: $orderHistoryData")
        orderHistoryAdapter = OrderHistoryAdapter(
            mContext,
            orderHistoryData,
            ::deleteItemInAdapter
        )
        binding?.recyclerOrderHistory?.apply {
            adapter = orderHistoryAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun deleteItemInAdapter(data: CoffeeResponseModel){
        BaseShared.clearCartItems(mContext)
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(
            mContext as AppCompatActivity,
            getString(R.string.order_history)
        )
    }

}