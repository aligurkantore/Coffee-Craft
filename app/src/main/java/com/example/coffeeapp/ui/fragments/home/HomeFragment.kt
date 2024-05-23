package com.example.coffeeapp.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
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
import com.example.coffeeapp.util.CoffeeUtil
import com.example.coffeeapp.util.Constants.Companion.DETAIL
import com.example.coffeeapp.util.Constants.Companion.RECYCLER_VIEW_TYPE
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.navigateSafeWithArgs
import com.example.coffeeapp.util.setupKeyboardHidingOnTouch
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

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
        setupKeyboardHidingOnTouch(view)
        setUpAdapters()
        searchCoffee()
        configureSearchView()
    }

    override fun setUpListeners() {}

    override fun setUpObservers() {
        viewModel.packetLiveData.observe(viewLifecycleOwner) {
            it?.let {
                setUpCoffeePacketAdapter(it)
            }
        }
    }

    private fun setUpAdapters() {
        setUpCategoryAdapter()
        setUpLocalCoffeesAdapter()
        setUpCakesAdapter()
        setUpCoffeeAdapter()
    }


    private fun setUpCategoryAdapter() {
        val categoryList = coffeeUtil.getCategoryNameList(mContext)
        categoryAdapter = CategoryAdapter(categoryList) { category ->
            val filteredCoffeeList = when (category) {
                in 1..6 -> coffeeUtil.getCoffeeList(mContext).drop((category - 1) * 3).take(3)
                else -> coffeeUtil.getCoffeeList(mContext).dropLast(11)
            }
            coffeeAdapter.updateCoffeeList(filteredCoffeeList)

        }
        binding?.recyclerViewCategory?.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setUpCoffeeAdapter() {
        coffeeList = coffeeUtil.getCoffeeList(mContext).dropLast(11).toMutableList()
        coffeeAdapter =
            CoffeeAdapter(
                mContext,
                coffeeList.toMutableList(),
                ::navigateToDetail,
                ::addToCartCoffee,
                ::addToFavoriteCoffee,
                "COFFEE_RECYCLERVIEW_TYPE",
                viewModel.isLoggedIn()
            )

        binding?.recyclerViewCoffee?.apply {
            adapter = coffeeAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpLocalCoffeesAdapter() {
        val coffeeList = coffeeUtil.getCoffeeList(mContext)
        val startIdStr = "39"
        val endIdStr = "44"

        val localCoffeeList = coffeeList.filter { it.id!! >= startIdStr && it.id <= endIdStr }


        coffeeAdapter = CoffeeAdapter(
            mContext,
            localCoffeeList.toMutableList(),
            ::navigateToDetail,
            ::addToCartCoffee,
            ::addToFavoriteCoffee,
            "COFFEE_LOCAL_RECYCLERVIEW_TYPE",
            viewModel.isLoggedIn()
        )

        binding?.recyclerLocalCoffees?.apply {
            adapter = coffeeAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpCakesAdapter() {
        val coffeeList = coffeeUtil.getCoffeeList(mContext)
        val startIdStr = "45"
        val endIdStr = "49"

        val cakeList = coffeeList.filter { it.id!! >= startIdStr && it.id <= endIdStr }


        coffeeAdapter = CoffeeAdapter(
            mContext,
            cakeList.toMutableList(),
            ::navigateToDetail,
            ::addToCartCoffee,
            ::addToFavoriteCoffee,
            "CAKE_RECYCLERVIEW_TYPE",
            viewModel.isLoggedIn()
        )

        binding?.recyclerCakes?.apply {
            adapter = coffeeAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setUpCoffeePacketAdapter(data: List<CoffeePacketResponseItem>) {
        coffeePacketAdapter = CoffeePacketAdapter(
            mContext,
            data
        )
        binding?.recyclerCoffeePacket?.apply {
            adapter = coffeePacketAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun searchCoffee() {
        binding?.searchView?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val filteredList = searchCoffeeByTitle(it)
                    coffeeAdapter.updateCoffeeList(filteredList)
                    updateRecyclerViewVisibility(filteredList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = searchCoffeeByTitle(it)
                    coffeeAdapter.updateCoffeeList(filteredList)
                    updateRecyclerViewVisibility(filteredList)
                }
                return true
            }
        })
    }

    private fun configureSearchView() {
        val searchView = binding?.searchView
        val searchEditText =
            searchView?.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText?.setTextColor(ContextCompat.getColor(requireContext(), R.color.is_selected))
        searchEditText?.setHintTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.is_selected
            )
        )
    }


    private fun searchCoffeeByTitle(query: String): MutableList<CoffeeResponseModel> {
        val filteredCoffeeList = mutableListOf<CoffeeResponseModel>()
        for (coffee in coffeeList) {
            if (coffee.name?.toLowerCase()?.contains(query.toLowerCase()) == true) {
                filteredCoffeeList.add(coffee)
            }
        }

        return filteredCoffeeList
    }

    private fun updateRecyclerViewVisibility(filteredList: MutableList<CoffeeResponseModel>) {
        checkItem(filteredList.isNotEmpty())
    }

    private fun checkItem(isVisible: Boolean) {
        viewBindingScope {
            recyclerViewCoffee visibleIf isVisible
            recyclerViewCategory visibleIf isVisible
            notFoundCoffee goneIf isVisible
        }
    }


    private fun navigateToDetail(data: CoffeeResponseModel, recyclerViewType: String) {
        val bundle = Bundle().apply {
            putSerializable(DETAIL, data)
            putString(RECYCLER_VIEW_TYPE, recyclerViewType)
        }
        navigateSafeWithArgs(R.id.action_homeFragment_to_detailFragment, bundle)
    }


    private fun addToCartCoffee(data: CoffeeResponseModel) {
        if (viewModel.isLoggedIn()) FireBaseDataManager.addToCart(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }


    private fun addToFavoriteCoffee(data: CoffeeResponseModel, hasAction: Boolean) {
        if (hasAction) FireBaseDataManager.toggleFavorite(mContext, data)
        else navigateSafe(R.id.action_homeFragment_to_loginFragment)
    }
}