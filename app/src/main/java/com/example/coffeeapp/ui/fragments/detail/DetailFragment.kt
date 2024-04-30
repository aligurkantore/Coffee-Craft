package com.example.coffeeapp.ui.fragments.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentDetailBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.detail.CoffeeSizeAdapter
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.Constants.Companion.L
import com.example.coffeeapp.util.Constants.Companion.M
import com.example.coffeeapp.util.Constants.Companion.S
import com.example.coffeeapp.util.navigateSafe


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private var coffeeSizeAdapter: CoffeeSizeAdapter? = null
    override val viewModelClass: Class<out DetailViewModel>
        get() = DetailViewModel::class.java


    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
    }


    @SuppressLint("StringFormatMatches")
    override fun setUpListeners() {
        val getDetail = arguments?.getSerializable(DETAIL) as? CoffeeResponseModel
        getDetail?.let { detail ->
            binding?.apply {
                detail.image_link_portrait?.let { imageCoffee.setImageResource(it) }
                textNameCoffe.text = detail.name
                textSpecialIngredientCoffee.text = detail.special_ingredient
                textAverageRating.text = detail.average_rating.toString()
                textRatingsCount.text = String.format("(%s)", detail.ratings_count)

                val sPrice =
                    mContext.getString(
                        R.string.price_format,
                        getDetail.prices?.firstOrNull()?.price
                    )
                textPrice.text = sPrice

                coffeeSizeAdapter =
                    detail.prices?.let { coffeePrices ->
                        CoffeeSizeAdapter(
                            coffeePrices,
                            object : CoffeeSizeAdapter.ItemClickListener {
                                override fun onSizeClicked(size: String) {
                                    val price = when (size) {
                                        S -> getDetail.prices?.firstOrNull { it.size == S }?.price
                                            ?: 0.0

                                        M -> getDetail.prices?.firstOrNull { it.size == M }?.price
                                            ?: 0.0

                                        L -> getDetail.prices?.firstOrNull { it.size == L }?.price
                                            ?: 0.0

                                        else -> 0.0
                                    }
                                    val formattedPrice =
                                        mContext.getString(R.string.price_format, price)
                                    textPrice.text = formattedPrice
                                }


                            })
                    }
                binding?.recyclerViewSize?.apply {
                    adapter = coffeeSizeAdapter
                    layoutManager =
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                }
                coffeeSizeAdapter?.notifyDataSetChanged()

                addToCart.setOnClickListener {
                    addToCart(detail)
                }

            }
        }
    }

    private fun addToCart(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) {
            val selectedSize = BaseShared.getString(mContext, "size_${data.id}", "")
            if (selectedSize?.isEmpty() == true) {
                BaseShared.saveString(mContext, "size_${data.id}", S)
            }
            FireBaseDataManager.addToCart(mContext, data)
        } else {
            navigateSafe(R.id.action_detailFragment_to_loginFragment)
        }
    }

    override fun setUpObservers() {
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.detail))
    }
}