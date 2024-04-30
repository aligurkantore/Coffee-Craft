package com.example.coffeeapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private var _binding: VB? = null
    protected val binding: VB?
        get() = _binding

    protected abstract val viewModelClass: Class<out VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB



    abstract fun setUpListeners()

    abstract fun setUpObservers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getBinding(inflater, container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
    }
/*
    private fun navigateToDestination(data: CoffeeResponseModel, actionId: Int) {
        val bundle = Bundle().apply {
            putSerializable(Constants.DETAIL, data)
        }
        navigateSafeWithArgs(actionId, bundle)
    }

 */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}