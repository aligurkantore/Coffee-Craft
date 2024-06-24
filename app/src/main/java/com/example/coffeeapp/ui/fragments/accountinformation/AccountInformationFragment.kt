package com.example.coffeeapp.ui.fragments.accountinformation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentAccountInformationBinding
import com.example.coffeeapp.util.Constants.Companion.EMAIL
import com.example.coffeeapp.util.Constants.Companion.NAME


class AccountInformationFragment :
    BaseFragment<FragmentAccountInformationBinding, AccountInformationViewModel>() {

    override val viewModelClass: Class<out AccountInformationViewModel>
        get() = AccountInformationViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAccountInformationBinding {
        return FragmentAccountInformationBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        val name = BaseShared.getString(mContext, NAME, "")
        val email = BaseShared.getString(mContext, EMAIL, "")

        binding?.apply {
            editTextName.setText(name)
            editTextEmail.setText(email)
        }
    }

    override fun setUpObservers() {
    }

}