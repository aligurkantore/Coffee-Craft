package com.example.coffeeapp.ui.fragments.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentDetailBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.helper.FireBaseDataManager.userId
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.Constants.Companion.RECYCLER_VIEW_TYPE
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.visible


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override val viewModelClass: Class<out DetailViewModel>
        get() = DetailViewModel::class.java


    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }


    override fun setUpListeners() {
        val getDetail = arguments?.getSerializable(DETAIL) as? CoffeeResponseModel
        getDetail?.let { detail ->
            viewBindingScope {
                detail.image_link_portrait?.let { imageCoffee.setImageResource(it) }
                textNameCoffe.text = detail.name
                textSpecialIngredientCoffee.text = detail.special_ingredient
                textAverageRating.text = detail.average_rating.toString()
                textRatingsCount.text = String.format("(%s)", detail.ratings_count)
                textDescription.text = detail.description
                val formattedPrice =
                    mContext.getString(R.string.price_format, detail.price)
                textPrice.text = formattedPrice

                addToCart.setOnClickListener { addToCart(detail) }
                imageFavorite.setOnClickListener { addToFavorite(detail) }
                updateFavoriteState(detail.id)

            }
        }

        val recyclerViewType = arguments?.getString(RECYCLER_VIEW_TYPE)
        if (recyclerViewType == "CAKE_RECYCLERVIEW_TYPE" || recyclerViewType == "COFFEE_LOCAL_RECYCLERVIEW_TYPE") {
            binding?.cardCoffee?.gone()
        } else {
            binding?.cardCoffee?.visible()
        }
    }

    private fun addToCart(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) {
            FireBaseDataManager.addToCart(mContext, data)
        } else {
            navigateSafe(R.id.action_detailFragment_to_loginFragment)
        }
    }

    private fun addToFavorite(data: CoffeeResponseModel) {
        val newFavoriteState = !BaseShared.getBoolean(
            requireContext(),
            "${userId}/favorite_${data.id}",
            false
        )
        if (viewModel.isLoggedIn()) {
            binding?.imageFavorite?.setImageResource(if (newFavoriteState) R.drawable.select_favorite_heart else R.drawable.heart)
            FireBaseDataManager.toggleFavorite(mContext, data)
            BaseShared.saveBoolean(
                requireContext(),
                "${userId}/favorite_${data.id}",
                newFavoriteState
            )
        } else {
            navigateSafe(R.id.action_detailFragment_to_loginFragment)
        }

    }

    private fun updateFavoriteState(id: String?) {
        val isFavorite = BaseShared.getBoolean(
            requireContext(),
            "${userId}/favorite_$id",
            false
        )
        binding?.imageFavorite?.setImageResource(if (isFavorite) R.drawable.select_favorite_heart else R.drawable.heart)
    }

    override fun setUpObservers() {}

}