package com.example.coffeeapp.ui.fragments.profile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentProfileBinding
import com.example.coffeeapp.models.profile.LanguageModel
import com.example.coffeeapp.ui.adapters.profile.LanguageAdapter
import com.example.coffeeapp.ui.adapters.profile.ProfileCategoryAdapter
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.CoffeeUtil
import com.example.coffeeapp.util.Constants.Companion.CATEGORYNAME
import com.example.coffeeapp.util.Constants.Companion.DE
import com.example.coffeeapp.util.Constants.Companion.EN
import com.example.coffeeapp.util.Constants.Companion.FR
import com.example.coffeeapp.util.Constants.Companion.LANG
import com.example.coffeeapp.util.Constants.Companion.TR
import com.example.coffeeapp.util.changeLanguage
import com.example.coffeeapp.util.navigateSafe

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private lateinit var coffeeUtil: CoffeeUtil
    private lateinit var profileCategoryAdapter: ProfileCategoryAdapter
    private lateinit var languageAdapter: LanguageAdapter
    private var dialog: Dialog? = null

    override val viewModelClass: Class<out ProfileViewModel>
        get() = ProfileViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coffeeUtil = CoffeeUtil()
        setProfileCategoryAdapter()
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.textChangePassword?.setOnClickListener {
            navigateSafe(R.id.action_profileFragment_to_changePasswordFragment)
        }
    }

    override fun setUpObservers() {
    }

    private fun setProfileCategoryAdapter() {

        val allCategories = coffeeUtil.getProfileCategoryList(mContext)
        val notLoggedInCustomer =
            allCategories.filterIndexed { index, _ -> listOf(0, 1, 2, 3, 4).contains(index) }
        val isLoggedIn = viewModel.isLoggedIn()
        val categories = if (isLoggedIn) allCategories else notLoggedInCustomer
        profileCategoryAdapter = ProfileCategoryAdapter(
            categories,
            object : ProfileCategoryAdapter.ItemClickCategoryListener {
                override fun onClickListener(categoryName: String, position: Int) {
                    BaseShared.saveString(mContext, CATEGORYNAME, categoryName)
                    val actionId = when {
                        position == 0 -> R.id.action_profileFragment_to_orderHistoryFragment
                        position == 1 -> R.id.action_profileFragment_to_cartFragment
                        position == 2 -> R.id.action_profileFragment_to_favoriteFragment
                        position == 3 -> R.id.action_profileFragment_to_paymentInformationFragment
                        isLoggedIn && position == 8 -> {
                            showLogoutDialog()
                            return
                        }

                        (isLoggedIn && position == 4) || (!isLoggedIn && position == 4) -> {
                            showLanguagePopUp()
                            return
                        }

                        (isLoggedIn && position == 5) || (!isLoggedIn && position == 5) -> {
                            //  showCurrencyPopUp()
                            return
                        }

                        else -> {
                            R.id.action_profileFragment_to_categoryDetailFragment
                        }
                    }
                    navigateSafe(actionId)
                }
            })
        binding?.recyclerViewCategory?.apply {
            adapter = profileCategoryAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun showLanguagePopUp() {
        val languageList = coffeeUtil.getLanguageList(mContext)
        languageAdapter = LanguageAdapter(languageList) { language ->
            when (language) {
                1 -> {
                    LanguageModel(
                        stringId = R.string.turkish,
                        lang = TR
                    )
                }

                2 -> {
                    LanguageModel(
                        stringId = R.string.german,
                        lang = DE
                    )
                }

                3 -> {
                    LanguageModel(
                        stringId = R.string.french,
                        lang = FR
                    )
                }

                else -> {
                    LanguageModel(
                        stringId = R.string.english,
                        lang = EN
                    )
                }
            }.also {
                mContext.changeLanguage(it.lang)
                BaseShared.saveString(mContext, LANG, it.lang)
            }
            requireActivity().recreate()
        }
        setPopUp(languageAdapter)
    }

    private fun setPopUp(adapter: RecyclerView.Adapter<*>) {
        dialog = Dialog(mContext)
        dialog?.apply {
            setContentView(R.layout.pop_up_language_currency)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val recyclerView = dialog?.findViewById<RecyclerView>(R.id.recycler_view_language_currency)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        dialog?.show()
    }

    private fun showLogoutDialog() {
        CustomDialog(
            mContext,
            getString(R.string.log_out),
            getString(R.string.log_out_dialog),
            getString(R.string.yes),
            getString(R.string.no),
            positiveButtonClickListener = {
                viewModel.auth.signOut()
                requireActivity().recreate()
            }
        ).show()
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(mContext as AppCompatActivity, getString(R.string.profile))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
    }

}