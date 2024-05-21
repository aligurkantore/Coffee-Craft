package com.example.coffeeapp.ui.fragments.myaddresses

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentMyAddressesBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.ui.adapters.address.MyAddressesAdapter
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visible
import com.example.coffeeapp.util.visibleIf


class MyAddressesFragment : BaseFragment<FragmentMyAddressesBinding, MyAddressesViewModel>() {

    private lateinit var addressAdapter: MyAddressesAdapter

    override val viewModelClass: Class<out MyAddressesViewModel>
        get() = MyAddressesViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMyAddressesBinding {
        return FragmentMyAddressesBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTouchListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpTouchListener() {
        binding?.apply {
            recyclerAddress.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val selectedItemPosition = addressAdapter.getSelectedItemPosition()
                    addressAdapter.setSelectedItemPosition(-1)
                    addressAdapter.notifyItemChanged(selectedItemPosition)
                    buttonAddNewAddress.visible()
                    buttonContinueWithAddress.gone()
                }
                false
            }
        }

    }


    override fun setUpListeners() {
        binding?.apply {
            buttonAddNewAddress.setOnClickListener {
                navigateSafe(R.id.action_myAddressesFragment_to_addAddressFragment)
            }
            buttonAddNewAddressEmpty.setOnClickListener {
                navigateSafe(R.id.action_myAddressesFragment_to_addAddressFragment)
            }
            buttonContinueWithAddress.setOnClickListener {
                navigateSafe(R.id.action_myAddressesFragment_to_paymentInformationFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.addressLiveData.observeNonNull(viewLifecycleOwner) { addressList ->
            if (addressList.isNullOrEmpty()) {
                setUIView(false)
            } else {
                setUIView(true)
                setUpAddressAdapter(addressList)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAddressAdapter(data: List<AddAddress>) {
        addressAdapter = MyAddressesAdapter(data, object : MyAddressesAdapter.ClickDeleteListener {
            override fun deleteListener(data: AddAddress) {
                FireBaseDataManager.deleteAddressData(data)
                binding?.buttonContinueWithAddress?.gone()
            }

        }) {
            binding?.apply {
                buttonAddNewAddress.gone()
                buttonContinueWithAddress.visible()
            }
        }
        binding?.recyclerAddress?.apply {
            adapter = addressAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }
        addressAdapter.notifyDataSetChanged()
        binding?.recyclerAddress?.smoothScrollToPosition(0)
    }

    private fun setUIView(isVisible: Boolean) {
        binding?.apply {
            recyclerAddress visibleIf isVisible
            buttonAddNewAddress visibleIf isVisible
            imageEmptyAddress goneIf isVisible
            textEmptyAddress goneIf isVisible
            buttonAddNewAddressEmpty goneIf isVisible
        }
    }

}