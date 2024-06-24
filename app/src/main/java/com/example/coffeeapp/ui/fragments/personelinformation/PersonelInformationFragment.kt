package com.example.coffeeapp.ui.fragments.personelinformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentPersonelInformationBinding
import com.example.coffeeapp.ui.adapters.profile.PersonelInformationAdapter
import com.example.coffeeapp.util.CoffeeUtil
import com.example.coffeeapp.util.navigateSafe
import javax.inject.Inject

class PersonelInformationFragment :
    BaseFragment<FragmentPersonelInformationBinding, PersonelInformationViewModel>() {

    @Inject
    lateinit var coffeeUtil: CoffeeUtil

    private lateinit var personelAdapter: PersonelInformationAdapter
    override val viewModelClass: Class<out PersonelInformationViewModel>
        get() = PersonelInformationViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPersonelInformationBinding {
        return FragmentPersonelInformationBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coffeeUtil = CoffeeUtil()
        setPersonelAdapter()
    }

    private fun setPersonelAdapter() {
        val categories = coffeeUtil.getPersonelInformationCategoryList(mContext)
        personelAdapter = PersonelInformationAdapter(
            categories,
            object : PersonelInformationAdapter.ItemClickCategoryListener {
                override fun onClickListener(categoryName: String, position: Int) {
                    val actionId = when {
                        position == 0 -> R.id.action_personelInformationFragment_to_accountInformationFragment
                        else -> {
                            R.id.action_personelInformationFragment_to_changePasswordFragment
                        }
                    }
                    navigateSafe(actionId)

                }
            }
        )
        binding?.recyclerViewCategory?.apply {
            adapter = personelAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }


}