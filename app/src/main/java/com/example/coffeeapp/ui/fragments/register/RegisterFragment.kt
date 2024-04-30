package com.example.coffeeapp.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentRegisterBinding
import com.example.coffeeapp.models.login.Register
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
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
        setupPasswordVisibilityToggle()
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonRegister.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                val confirmPassword = editTextPassword.text.toString()
                val register = Register(name, email, password, confirmPassword)
                viewModel.registerUser(register)
            }

            textViewContinueWithLogin.setOnClickListener {
                navigateSafe(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.apply {
            register.observeNonNull(viewLifecycleOwner) {
                if (it) navigateSafe(R.id.action_registerFragment_to_homeFragment)
                else showMessage(mContext, getString(R.string.register_failed))
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding?.apply {
            editTextPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
            editTextConfirmPassword.apply {
                setOnClickListener { togglePasswordVisibility() }
            }
        }
    }


    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.register))
    }

}