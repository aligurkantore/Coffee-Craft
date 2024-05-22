package com.example.coffeeapp.ui.fragments.orderhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentOrderHistoryBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.ui.adapters.orderhistory.OrderHistoryAdapter
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.visibleIf

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
        isUserLoggedIn(viewModel.isLoggedIn())
    }


    override fun setUpListeners() {
        binding?.buttonGoToHomePage?.setOnClickListener {
            navigateSafe(R.id.action_orderHistoryFragment_to_homeFragment)
        }
    }

    override fun setUpObservers() {
        viewModel.orderHistoryLiveData.observeNonNull(viewLifecycleOwner) { orderHistory ->
            if (!orderHistory.isNullOrEmpty()) {
                setUpOrderHistoryAdapter(orderHistory)
                setUIView(true)
            } else {
                setUIView(false)
            }
        }
    }

    private fun setUpOrderHistoryAdapter(data: List<OrderModel>) {
        orderHistoryAdapter = OrderHistoryAdapter(
            mContext,
            data,
            ::deleteItemInAdapter
        )
        binding?.recyclerOrderHistory?.apply {
            adapter = orderHistoryAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }
        orderHistoryAdapter.notifyDataSetChanged()
    }

    private fun deleteItemInAdapter(data: OrderModel) {
        FireBaseDataManager.removeFromOrderHistory(mContext,data.orderId)
    }

    private fun setUIView(isVisible: Boolean){
        binding?.apply {
            recyclerOrderHistory visibleIf isVisible
            imageEmptyOrderHistory goneIf isVisible
            textEmptyOrderHistory goneIf isVisible
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean){
        binding?.apply {
            recyclerOrderHistory visibleIf isLogin
            textNotLoggedIn goneIf isLogin
            buttonLogin goneIf isLogin
            buttonGoToHomePage visibleIf isLogin
        }
    }

}