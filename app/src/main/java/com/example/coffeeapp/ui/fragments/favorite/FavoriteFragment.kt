package com.example.coffeeapp.ui.fragments.favorite

import android.os.Bundle
import android.os.Parcelable
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
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    private var favoriteAdapter: FavoriteAdapter? = null
    private var recyclerViewState: Parcelable? = null

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
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    override fun setUpListeners() {}

    override fun setUpObservers() {
        viewModelScope {
            favoriteItemsLiveData.observeNonNull(viewLifecycleOwner) { list ->
                if (viewModel.isLoggedIn()) {
                    if (list.isEmpty()) {
                        setUIView(false)
                        binding?.baseEmptyView?.buttonAction?.gone()
                    } else {
                        setUIView(true)
                        setUpFavoriteAdapter(list)
                    }
                } else clearFavorite()

            }
            authStateLiveData.observeNonNull(viewLifecycleOwner) { isLoggedIn ->
                if (!isLoggedIn) {
                    clearFavorite()
                    isUserLoggedIn(false)
                }
            }
        }

    }


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
        if (recyclerViewState != null) {
            view?.post {
                binding?.recyclerFavorite?.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        }
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

    private fun setUIView(isVisible: Boolean) {
        viewBindingScope {
            recyclerFavorite visibleIf isVisible
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.coffee_favorite)
                textEmpty.text = getString(R.string.empty_favorites)

            }.also {
                it.constraintBaseEmpty goneIf isVisible
            }
        }
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            recyclerFavorite visibleIf isLogin
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.coffee_favorite)
                textEmpty.text = getString(R.string.must_login)
                buttonAction.text = getString(R.string.login)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isLogin
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_favoriteFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private fun clearFavorite() {
        favoriteAdapter?.notifyDataSetChanged()
        setUIView(false)
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding?.recyclerFavorite?.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            binding?.recyclerFavorite?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }
    }

}