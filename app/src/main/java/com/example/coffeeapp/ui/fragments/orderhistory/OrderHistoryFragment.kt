package com.example.coffeeapp.ui.fragments.orderhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentOrderHistoryBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.ui.adapters.orderhistory.OrderHistoryAdapter
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            if (viewModel.isLoggedIn()) {
                if (!orderHistory.isNullOrEmpty()) {
                    setUpOrderHistoryAdapter(orderHistory)
                    setUIView(true)
                } else {
                    setUIView(false)
                    binding?.baseEmptyView?.buttonAction?.gone()
                }
            } else isUserLoggedIn(false)

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
        FireBaseDataManager.removeFromOrderHistory(mContext, data.orderId)
        showMessage(mContext,getString(R.string.order_detail_deleted))
    }

    private fun setUIView(isVisible: Boolean) {
        viewBindingScope {
            recyclerOrderHistory visibleIf isVisible
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.order_history_list)
                textEmpty.text = getString(R.string.empty_order_history)

            }.also {
                it.constraintBaseEmpty goneIf isVisible
            }
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            recyclerOrderHistory visibleIf isLogin
            buttonGoToHomePage visibleIf isLogin
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.order_history_list)
                textEmpty.text = getString(R.string.must_login)
                buttonAction.text = getString(R.string.login)
            }.also {
                it.constraintBaseEmpty goneIf isLogin
                it.buttonAction.setOnClickListener {
                    navigateSafe(R.id.action_orderHistoryFragment_to_loginFragment)
                }
            }
        }
    }

}