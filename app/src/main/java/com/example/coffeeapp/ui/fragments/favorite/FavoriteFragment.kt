package com.example.coffeeapp.ui.fragments.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentFavoriteBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.helper.FireBaseDataManager.userId
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.favorite.FavoriteAdapter
import com.example.coffeeapp.util.Constants
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visibleIf


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    private lateinit var favoriteAdapter: FavoriteAdapter

    override val viewModelClass: Class<out FavoriteViewModel>
        get() = FavoriteViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startAuthStateListener()
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_favoriteFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.favoriteItemsLiveData.observeNonNull(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                checkItemInAdapter(false)
            } else {
                checkItemInAdapter(true)
                setUpFavoriteAdapter(list)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFavoriteAdapter(data: List<CoffeeResponseModel>) {
        favoriteAdapter = FavoriteAdapter(
            data,
            ::navigateToDetail,
            ::removeFromFavorites
        )
        binding?.recyclerFavorite?.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
        favoriteAdapter.notifyDataSetChanged()
    }

    private fun navigateToDetail(data: CoffeeResponseModel) {
        val bundle = Bundle().apply {
            putSerializable(Constants.DETAIL, data)
        }
        navigateSafeWithArgs(R.id.action_favoriteFragment_to_detailFragment, bundle)
    }

    private fun removeFromFavorites(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) {
            FireBaseDataManager.removeFromFavorite(
                mContext,
                data.id.toString()
            )
            BaseShared.removeKey(mContext, "${userId}/favorite_${data.id}")
        } else navigateSafe(R.id.action_favoriteFragment_to_loginFragment)
    }

    private fun checkItemInAdapter(isVisible: Boolean) {
        binding?.apply {
            recyclerFavorite visibleIf isVisible
            imageEmptyFavorite goneIf isVisible
            textEmptyFavorite goneIf isVisible
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        binding?.apply {
            imageEmptyFavorite goneIf isLogin
            textNotLoggedIn goneIf isLogin
            buttonLogin goneIf isLogin
            recyclerFavorite visibleIf isLogin
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopAuthStateListener()
    }

}