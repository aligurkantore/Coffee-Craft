package com.example.coffeeapp.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentLoginBinding
import com.example.coffeeapp.models.login.Login
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.NavigationManager
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.setUpBottomSheetDialog
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.togglePasswordVisibility
import com.google.android.material.snackbar.Snackbar


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModelClass: Class<out LoginViewModel>
        get() = LoginViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
        setupPasswordVisibilityToggle()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()
                val login = Login(email, password)
                viewModel.loginUser(login)
            }

            textViewContinueWithRegister.setOnClickListener {
                navigateSafe(R.id.action_loginFragment_to_registerFragment)
            }

            textViewForgotPassword.setOnClickListener {
                setUpBottomSheetDialog { email ->
                    viewModel.resetPassword(email)
                }
            }
        }
    }

    override fun setUpObservers() {
        viewModel.apply {
            login.observeNonNull(viewLifecycleOwner) {
                if (it) NavigationManager.navigateBackAfterLogin(findNavController())
                else showMessage(mContext, getString(R.string.login_failed))
            }

            resetPassword.observeNonNull(viewLifecycleOwner) { result ->
                if (result.isNotEmpty()) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.send_mail),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.empyt_mail),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding?.editTextPassword?.apply {
            setOnClickListener {
                togglePasswordVisibility()
            }
        }
    }


    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.login))
    }

}