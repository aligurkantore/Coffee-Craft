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
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.visible
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpTouchListener() {
        viewBindingScope {
            recyclerAddress.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val selectedItemPosition = addressAdapter.getSelectedItemPosition()
                    addressAdapter.setSelectedItemPosition(-1)
                    addressAdapter.notifyItemChanged(selectedItemPosition)
                    buttonAddNewAddress.visible()
                    buttonAddNewAddress.apply {
                        text = getString(R.string.add_new_address)
                        setOnClickListener {
                            navigateSafe(R.id.action_myAddressesFragment_to_addAddressFragment)
                        }
                    }
                }
                false
            }
        }

    }


    override fun setUpListeners() {
        viewBindingScope {
            buttonAddNewAddress.setOnClickListener {
                navigateSafe(R.id.action_myAddressesFragment_to_addAddressFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.addressLiveData.observeNonNull(viewLifecycleOwner) { addressList ->
            if (viewModel.isLoggedIn()) {
                if (addressList.isNullOrEmpty()) {
                    setUIView(false)
                } else {
                    setUIView(true)
                    setUpAddressAdapter(addressList)
                }
            } else isUserLoggedIn(false)

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAddressAdapter(data: List<AddAddress>) {
        addressAdapter = MyAddressesAdapter(data, object : MyAddressesAdapter.ClickDeleteListener {
            override fun deleteListener(data: AddAddress) {
                FireBaseDataManager.deleteAddressData(data)
                showMessage(mContext,getString(R.string.address_deleted))
            }

        }) {
            viewBindingScope {
                buttonAddNewAddress.apply {
                    text = getString(R.string.continue_with_address)
                    setOnClickListener {
                        navigateSafe(R.id.action_myAddressesFragment_to_paymentInformationFragment)
                    }
                }
                buttonAddNewAddress.text = getString(R.string.continue_with_address)
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
        viewBindingScope {
            recyclerAddress visibleIf isVisible
            buttonAddNewAddress visibleIf isVisible
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.location_profile)
                textEmpty.text = getString(R.string.no_address_record)
                buttonAction.text = getString(R.string.add_new_address)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isVisible
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_myAddressesFragment_to_addAddressFragment)
                    }
                }
            }
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            recyclerAddress visibleIf isLogin
            buttonAddNewAddress visibleIf isLogin
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.location_profile)
                textEmpty.text = getString(R.string.must_login)
                buttonAction.text = getString(R.string.login)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isLogin
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_myAddressesFragment_to_loginFragment)
                    }
                }
            }
        }
    }

}