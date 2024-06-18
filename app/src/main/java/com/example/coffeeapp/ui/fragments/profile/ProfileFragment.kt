package com.example.coffeeapp.ui.fragments.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentProfileBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.profile.LanguageModel
import com.example.coffeeapp.ui.adapters.profile.LanguageAdapter
import com.example.coffeeapp.ui.adapters.profile.ProfileCategoryAdapter
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.CoffeeUtil
import com.example.coffeeapp.util.Constants.Companion.DE
import com.example.coffeeapp.util.Constants.Companion.EMAIL
import com.example.coffeeapp.util.Constants.Companion.EN
import com.example.coffeeapp.util.Constants.Companion.FR
import com.example.coffeeapp.util.Constants.Companion.LANG
import com.example.coffeeapp.util.Constants.Companion.TR
import com.example.coffeeapp.util.changeLanguage
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    @Inject
    lateinit var coffeeUtil: CoffeeUtil

    private lateinit var profileCategoryAdapter: ProfileCategoryAdapter
    private lateinit var languageAdapter: LanguageAdapter
    private var dialog: Dialog? = null
    private lateinit var data: CoffeeResponseModel

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
        data = CoffeeResponseModel()
        coffeeUtil = CoffeeUtil()
        setProfileCategoryAdapter()
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    override fun setUpListeners() {
        viewBindingScope {
            textChangePassword.setOnClickListener {
                navigateSafe(R.id.action_profileFragment_to_changePasswordFragment)
            }
            buttonLogin.setOnClickListener {
                navigateSafe(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    override fun setUpObservers() {
    }

    private fun setProfileCategoryAdapter() {

        val allCategories = coffeeUtil.getProfileCategoryList(mContext)
        val notLoggedInCustomer =
            allCategories.filterIndexed { it, _ -> listOf(0, 1, 2, 3, 4, 5).contains(it) }
        val isLoggedIn = viewModel.isLoggedIn()
        val categories = if (isLoggedIn) allCategories else notLoggedInCustomer
        profileCategoryAdapter = ProfileCategoryAdapter(
            categories,
            object : ProfileCategoryAdapter.ItemClickCategoryListener {
                override fun onClickListener(categoryName: String, position: Int) {
                    val actionId = when {
                        position == 0 -> R.id.action_profileFragment_to_myAddressesFragment
                        position == 1 -> R.id.action_profileFragment_to_orderHistoryFragment
                        position == 2 -> R.id.action_profileFragment_to_cartFragment
                        position == 3 -> R.id.action_profileFragment_to_favoriteFragment
                        position == 4 -> R.id.action_profileFragment_to_paymentInformationFragment
                        isLoggedIn && position == 7 -> {
                            showLogoutDialog()
                            return
                        }

                        position == 5 -> {
                            showLanguagePopUp()
                            return
                        }

                        else -> {
                            R.id.action_profileFragment_to_aboutTheApplicationFragment
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
        languageAdapter = LanguageAdapter(mContext, languageList) { language ->
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
                it.lang?.let { lang ->
                    mContext.changeLanguage(lang)
                    BaseShared.saveString(mContext, LANG, lang)
                }
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

    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            buttonLogin goneIf isLogin
            cardViewPersonelInformation visibleIf isLogin
            val email: String? = BaseShared.getString(mContext, EMAIL, "")
            textUserEmail.text = email
        }
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
                BaseShared.removeKey(mContext, EMAIL)
                requireActivity().recreate()
            }
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
    }

}