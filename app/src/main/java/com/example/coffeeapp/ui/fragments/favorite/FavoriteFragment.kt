package com.example.coffeeapp.ui.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentFavoriteBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.ui.adapters.favorite.FavoriteAdapter
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.Constants
import com.example.coffeeapp.util.NavigationManager
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.visibleIf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteItems: MutableList<CoffeeResponseModel> = mutableListOf()
    private val dataBase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

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
        if (viewModel.isLoggedIn()) {
            isUserLoggedIn(true)
            setUpFavoriteAdapter()
            getFavoritesFromDataBase()
        } else {
            isUserLoggedIn(false)
        }
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonLogin.setOnClickListener {
                NavigationManager.apply {
                    setCurrentFragmentId(R.id.favoriteFragment)
                    navigateToLogin(findNavController())
                }
            }
        }
    }

    override fun setUpObservers() {
    }

    private fun getFavoritesFromDataBase() {
        val userId = Firebase.auth.currentUser?.uid
        val userRef: DatabaseReference = dataBase.getReference("users/$userId/favorite")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    if (item != null) {
                        tempList.add(item)
                    }
                }
                favoriteItems.clear()
                favoriteItems.addAll(tempList)
                favoriteAdapter.notifyDataSetChanged()
                checkItemInAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled ")
            }
        })
    }

    private fun setUpFavoriteAdapter() {
        favoriteAdapter = FavoriteAdapter(
            favoriteItems,
            ::navigateToDetail,
            ::removeFromFavorites
        )
        binding?.recyclerFavorite?.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun navigateToDetail(data: CoffeeResponseModel) {
        val bundle = Bundle().apply {
            putSerializable(Constants.DETAIL, data)
        }
        navigateSafeWithArgs(R.id.action_favoriteFragment_to_detailFragment, bundle)
    }

    private fun removeFromFavorites(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.removeFromFavorite(
            mContext,
            data.id.toString()
        )
        else navigateSafe(R.id.action_favoriteFragment_to_loginFragment)
    }

    private fun checkItemInAdapter() {
        val isEmpty = favoriteItems.isEmpty()
        binding?.apply {
            recyclerFavorite.visibility = if (isEmpty) View.GONE else View.VISIBLE
            imageEmptyFavorite.visibility = if (isEmpty) View.VISIBLE else View.GONE
            textEmptyFavorite.visibility = if (isEmpty) View.VISIBLE else View.GONE
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

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(
            mContext as AppCompatActivity,
            getString(R.string.my_favorites)
        )
    }
}