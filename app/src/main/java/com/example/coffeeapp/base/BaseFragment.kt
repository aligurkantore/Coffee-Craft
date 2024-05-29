package com.example.coffeeapp.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.coffeeapp.R
import com.example.coffeeapp.helper.NetWorkManager
import com.example.coffeeapp.ui.dialogs.CustomDialog

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
    private lateinit var netWorkManager: NetWorkManager

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
        netWorkManager = NetWorkManager()
        //  progressBarUtil = ProgressBarUtil(mContext, view as ViewGroup)
        setUpListeners()
        setUpObservers()

        if (netWorkManager.isInternetAvailable(mContext)) {
            Log.d("agt", "There is an Internet connection ")
        } else {
            Log.d("agt", "No Internet connection ")
            showNoInternetDialog()
        }
    }
    private fun showNoInternetDialog() {
        val dialog = CustomDialog(
            mContext,
            getString(R.string.warning),
            getString(R.string.no_internet),
            getString(R.string.ok),
            showNegativeButton = false,
            positiveButtonClickListener = {
                activity?.finishAffinity()
            }
        )
        dialog.apply {
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    protected inline fun viewModelScope(action: VM.() -> Unit) {
        action(viewModel)
    }

    protected inline fun viewBindingScope(action: VB.() -> Unit) {
        binding?.let { action(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}