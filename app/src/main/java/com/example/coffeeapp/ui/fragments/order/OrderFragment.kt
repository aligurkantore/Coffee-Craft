package com.example.coffeeapp.ui.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentOrderBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.navigateSafe


class OrderFragment :
    BaseFragment<FragmentOrderBinding, OrderViewModel>() {

    private var success: String = "successOrder"
    private lateinit var coffeeList: MutableList<CoffeeResponseModel>

    override val viewModelClass: Class<out OrderViewModel>
        get() = OrderViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonGoToHomePage.setOnClickListener {
                BaseShared.saveString(mContext,"successOrder", success)
                navigateSafe(R.id.action_orderFragment_to_homeFragment)
            }
            buttonGoToOrderHistory.setOnClickListener {
                BaseShared.saveString(mContext,"successOrder", success)
                navigateSafe(R.id.action_orderFragment_to_orderHistoryFragment)
            }
        }
    }

    override fun setUpObservers() {}

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.my_order))
    }

}