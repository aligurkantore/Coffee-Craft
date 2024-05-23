package com.example.coffeeapp.ui.fragments.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentChangePasswordBinding
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.togglePasswordVisibility


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPasswordVisibilityToggle()
    }

    override fun setUpListeners() {
        viewBindingScope {
            buttonChangePassword.setOnClickListener {
                val oldPassword = editTextOldPassword.text.toString()
                val newPassword = editTextNewPassword.text.toString()
                val newPasswordAgain = editTextNewPasswordAgain.text.toString()

                if (oldPassword.isBlank() || newPassword.isBlank() || newPasswordAgain.isBlank()) {
                    showMessage(mContext, getString(R.string.please_fill_all_fields))
                } else if (newPassword != newPasswordAgain) {
                    showMessage(mContext, getString(R.string.passwords_do_not_match))
                } else {
                    viewModel.changePassword(oldPassword, newPassword)
                }
            }
        }
    }

    override fun setUpObservers() {
        viewModel.passwordChangeLiveData.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                showMessage(mContext, getString(R.string.password_changed_successfully))
                navigateSafe(R.id.action_changePasswordFragment_to_profileFragment)
            } else {
                showMessage(mContext, getString(R.string.password_change_failed))
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        viewBindingScope {
            editTextOldPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
            editTextNewPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
            editTextNewPasswordAgain.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
        }
    }

}