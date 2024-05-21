package com.example.coffeeapp.ui.fragments.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentOrderHistoryBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.ui.adapters.orderhistory.OrderHistoryAdapter
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visible

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


    override fun setUpListeners() {
        binding?.buttonGoToHomePage?.setOnClickListener {
            navigateSafe(R.id.action_orderHistoryFragment_to_homeFragment)
        }
    }

    override fun setUpObservers() {
        viewModel.orderHistoryLiveData.observeNonNull(viewLifecycleOwner) { orderHistory ->
            if (!orderHistory.isNullOrEmpty()) {
                setUpOrderHistoryAdapter(orderHistory)
                binding?.textEmptyOrderHistory?.gone()
            } else {
                binding?.textEmptyOrderHistory?.visible()
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

}