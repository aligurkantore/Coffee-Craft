package com.example.coffeeapp.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentHomeBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponseItem
import com.example.coffeeapp.ui.adapters.home.CategoryAdapter
import com.example.coffeeapp.ui.adapters.home.CoffeeAdapter
import com.example.coffeeapp.ui.adapters.home.CoffeePacketAdapter
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.CoffeeUtil
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coffeeAdapter: CoffeeAdapter
    private lateinit var coffeePacketAdapter: CoffeePacketAdapter
    private val coffeeUtil = CoffeeUtil()
    private lateinit var coffeeList: MutableList<CoffeeResponseModel>

    override val viewModelClass: Class<out HomeViewModel>
        get() = HomeViewModel::class.java


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  progressBarUtil.showProgressBar()
        setUpCategoryAdapter()
        setUpCoffeeAdapter()
        searchCoffee()
        setUpAppBar()
    }

    override fun setUpListeners() {}

    override fun setUpObservers() {
        viewModel.packetLiveData.observe(viewLifecycleOwner) {
            it?.let {
                setUpCoffeePacketAdapter(it)
                // progressBarUtil.hideProgressBar()
            }
        }
    }


    private fun setUpCategoryAdapter() {
        val categoryList = coffeeUtil.getCategoryNameList(mContext)
        categoryAdapter = CategoryAdapter(categoryList) { category ->
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                val filteredCoffeeList = when (category) {
                    in 1..6 -> coffeeUtil.getCoffeeList(mContext).drop((category - 1) * 3).take(3)
                    else -> coffeeUtil.getCoffeeList(mContext)
                }
                coffeeAdapter.updateCoffeeList(filteredCoffeeList)
            }

        }
        binding?.recyclerViewCategory?.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setUpCoffeeAdapter() {
        coffeeList = coffeeUtil.getCoffeeList(mContext)
        coffeeAdapter =
            CoffeeAdapter(
                mContext,
                coffeeList,
                ::navigateToDetail,
                ::addToCartCoffee,
                ::addToFavoriteCoffee
            )
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            binding?.recyclerViewCoffee?.apply {
                adapter = coffeeAdapter
                layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }


    private fun setUpCoffeePacketAdapter(data: List<CoffeePacketResponseItem>) {
        coffeePacketAdapter = CoffeePacketAdapter(
            mContext,
            data,
            ::addToCartCoffeePacket,
            ::addToFavoriteCoffeePacket
        )
        binding?.recyclerCoffeePacket?.apply {
            adapter = coffeePacketAdapter
            layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun searchCoffee() {
        binding?.searchView?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val filteredList = searchCoffeeByTitle(it)
                    coffeeAdapter.updateCoffeeList(filteredList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = searchCoffeeByTitle(it)
                    coffeeAdapter.updateCoffeeList(filteredList)
                }
                return true
            }
        })
    }


    private fun searchCoffeeByTitle(query: String): MutableList<CoffeeResponseModel> {
        val filteredCoffeeList = mutableListOf<CoffeeResponseModel>()
        for (coffee in coffeeList) {
            if (coffee.name?.toLowerCase()?.contains(query.toLowerCase()) == true) {
                filteredCoffeeList.add(coffee)
            }
        }

        if (filteredCoffeeList.isEmpty()) {
            binding?.apply {
                recyclerViewCategory.gone()
                notFoundCoffee.visible()
            }
        }

        return filteredCoffeeList
    }


    private fun navigateToDetail(data: CoffeeResponseModel) {
        val bundle = Bundle().apply {
            putSerializable(DETAIL, data)
        }
        navigateSafeWithArgs(R.id.action_homeFragment_to_detailFragment, bundle)
    }


    private fun addToCartCoffee(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToCart(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }


    private fun addToFavoriteCoffee(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToFavorite(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }

    private fun addToCartCoffeePacket(data: CoffeePacketResponseItem) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToCart(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }

    private fun addToFavoriteCoffeePacket(data: CoffeePacketResponseItem) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToFavorite(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }


    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.home))
    }
}