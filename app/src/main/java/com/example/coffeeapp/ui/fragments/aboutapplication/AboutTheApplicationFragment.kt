package com.example.coffeeapp.ui.fragments.aboutapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentAboutTheApplicationBinding


class AboutTheApplicationFragment :
    BaseFragment<FragmentAboutTheApplicationBinding, AboutTheApplicationViewModel>() {

    override val viewModelClass: Class<out AboutTheApplicationViewModel>
        get() = AboutTheApplicationViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAboutTheApplicationBinding {
        return FragmentAboutTheApplicationBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        viewBindingScope {
            imageApp.setImageResource(R.drawable.coffee)
            textAppName.text = getString(R.string.app_name)
            appDescription.text = getString(R.string.app_description)
        }
    }

    override fun setUpObservers() {}

}