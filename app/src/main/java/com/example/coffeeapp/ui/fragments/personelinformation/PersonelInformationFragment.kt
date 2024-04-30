package com.example.coffeeapp.ui.fragments.personelinformation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentPersonelInformationBinding


class PersonelInformationFragment : BaseFragment<FragmentPersonelInformationBinding, PersonelInformationViewModel>() {

    override val viewModelClass: Class<out PersonelInformationViewModel>
        get() = PersonelInformationViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPersonelInformationBinding {
        TODO("Not yet implemented")
    }

    override fun setUpListeners() {
        TODO("Not yet implemented")
    }

    override fun setUpObservers() {
        TODO("Not yet implemented")
    }

}