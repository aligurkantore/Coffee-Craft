package com.example.coffeeapp.ui.fragments.categorydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentCategoryDetailBinding
import com.example.coffeeapp.util.Constants.Companion.CATEGORY_NAME


class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding, CategoryDetailViewModel>() {

    override val viewModelClass: Class<out CategoryDetailViewModel>
        get() = CategoryDetailViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCategoryDetailBinding {
        return FragmentCategoryDetailBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        val categoryName = BaseShared.getString(mContext, CATEGORY_NAME,"")
        binding?.apply {
            textVieewCategory.text = categoryName
        }
    }

    override fun setUpObservers() {}

}