package com.example.coffeeapp.ui.fragments.order

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentOrderBinding
import com.example.coffeeapp.util.navigateSafe


class OrderFragment :
    BaseFragment<FragmentOrderBinding, OrderViewModel>() {


    override val viewModelClass: Class<out OrderViewModel>
        get() = OrderViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonGoToHomePage.setOnClickListener {
                //FireBaseDataManager.moveCartToOrderHistory(mContext)
                navigateSafe(R.id.action_orderFragment_to_homeFragment)
            }
            buttonGoToOrderHistory.setOnClickListener {
                //FireBaseDataManager.moveCartToOrderHistory(mContext)
                navigateSafe(R.id.action_orderFragment_to_orderHistoryFragment)
            }
        }
    }

    override fun setUpObservers() {}

}