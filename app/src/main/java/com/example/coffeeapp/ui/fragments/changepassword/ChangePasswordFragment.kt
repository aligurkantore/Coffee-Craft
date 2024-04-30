package com.example.coffeeapp.ui.fragments.changepassword

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>() {

    override val viewModelClass: Class<out ChangePasswordViewModel>
        get() = ChangePasswordViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.inflate(inflater)
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }


}