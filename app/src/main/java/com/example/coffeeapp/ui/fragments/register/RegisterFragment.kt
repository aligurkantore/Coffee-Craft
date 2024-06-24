package com.example.coffeeapp.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentRegisterBinding
import com.example.coffeeapp.models.login.Register
import com.example.coffeeapp.util.Constants.Companion.EMAIL
import com.example.coffeeapp.util.Constants.Companion.NAME
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.setupKeyboardHidingOnTouch
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.togglePasswordVisibility


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModelClass: Class<out RegisterViewModel>
        get() = RegisterViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupKeyboardHidingOnTouch(view)
        setupPasswordVisibilityToggle()
    }

    override fun setUpListeners() {
        viewBindingScope {
            buttonRegister.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                val confirmPassword = editTextConfirmPassword.text.toString()
                val register = Register(name, email, password, confirmPassword)
                viewModel.registerUser(register)
                BaseShared.saveString(mContext, NAME, name)
                BaseShared.saveString(mContext, EMAIL, email)
            }

            textViewContinueWithLogin.setOnClickListener {
                navigateSafe(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModelScope {
            register.observeNonNull(viewLifecycleOwner) { success ->
                if (success) navigateSafe(R.id.action_registerFragment_to_homeFragment)
                else showMessage(mContext, getString(R.string.register_failed))
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        viewBindingScope {
            editTextPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
            editTextConfirmPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
        }
    }

    private fun clearEditTextFields() {
        viewBindingScope {
            editTextName.text.clear()
            editTextEmail.text.clear()
            editTextPassword.text.clear()
            editTextConfirmPassword.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        clearEditTextFields()
    }

}