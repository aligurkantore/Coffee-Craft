package com.example.coffeeapp.ui.fragments.paymentinformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentPaymentInformationBinding
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.Constants.Companion.CARDNAME
import com.example.coffeeapp.util.Constants.Companion.CARDNUMBER
import com.example.coffeeapp.util.Constants.Companion.CARDEXPIRATIONDATE
import com.example.coffeeapp.util.NavigationManager
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.visible
import com.example.coffeeapp.util.visibleIf


class PaymentInformationFragment :
    BaseFragment<FragmentPaymentInformationBinding, PaymentInformationViewModel>() {

    override val viewModelClass: Class<out PaymentInformationViewModel>
        get() = PaymentInformationViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPaymentInformationBinding {
        return FragmentPaymentInformationBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressed()
        setUpAppBar()
        isUserLoggedIn(viewModel.isLoggedIn())
    }

    override fun setUpListeners() {
        binding?.apply {
            val clickListeners = mapOf<View, Int>(
                linearLayoutWallet to R.string.pay_from_wallet,
                constraintCreditCard to R.string.pay_from_credit_card,
                linearLayoutGooglePay to R.string.pay_from_google,
                linearLayoutAmazonPay to R.string.pay_from_amazon
            )

            clickListeners.forEach { (view, textResId) ->
                view.setOnClickListener {

                    val scale = 1.1f
                    view.animate().scaleX(scale).scaleY(scale).setDuration(200).start()

                    clickListeners.keys.filter { it != view }.forEach {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                    }
                    textTitlePrice.visible()
                    textPrice.visible()
                    buttonPay.visible()
                    buttonPay.text = mContext.getString(textResId)
                }
            }

            linearLayoutAddCard.setOnClickListener {
                navigateSafe(R.id.action_paymentInformationFragment_to_addCardFragment)
            }

            deleteCreditCart.setOnClickListener {
                showDeleteCreditCardDialog()
            }

            buttonLogin.setOnClickListener {
                NavigationManager.apply {
                    setCurrentFragmentId(R.id.paymentInformationFragment)
                    navigateToLogin(findNavController())
                }
            }

            buttonPay.setOnClickListener {
                navigateSafe(R.id.action_paymentInformationFragment_to_orderFragment)
            }
            showSavedCardInfo()
            showTotalPrice()
        }
    }

    override fun setUpObservers() {
    }

    private fun updateCreditCardVisibility(visible: Boolean) {
        binding?.apply {
            constraintCreditCard visibleIf visible
            deleteCreditCart visibleIf visible
        }
    }

    private fun showSavedCardInfo() {
        val saveCardName = BaseShared.getString(mContext, CARDNAME, "")
        val saveCardNumber = BaseShared.getString(mContext, CARDNUMBER, "")
        val saveCardExpirationDate = BaseShared.getString(mContext, CARDEXPIRATIONDATE, "")

        if (saveCardName?.isNotEmpty() == true && saveCardNumber?.isNotEmpty() == true &&
            saveCardExpirationDate?.isNotEmpty() == true
        ) {
            binding?.apply {
                textCardHolderName.text = saveCardName
                textNumberCreditCard.text = saveCardNumber
                textExpiryDate.text = saveCardExpirationDate
            }
            updateCreditCardVisibility(true)
        } else {
            updateCreditCardVisibility(false)
        }
    }

    private fun deleteCreditCard() {
        BaseShared.removeKey(mContext, CARDNAME)
        BaseShared.removeKey(mContext, CARDNUMBER)
        BaseShared.removeKey(mContext, CARDEXPIRATIONDATE)
        updateCreditCardVisibility(false)
    }

    private fun showTotalPrice() {
        val totalPrice = BaseShared.getString(mContext, "totalPrice", "")
        binding?.textPrice?.text = totalPrice
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        binding?.apply {
            constraintCreditCard visibleIf isLogin
            deleteCreditCart visibleIf isLogin
            linearLayoutWallet visibleIf isLogin
            linearLayoutGooglePay visibleIf isLogin
            linearLayoutAmazonPay visibleIf isLogin
            linearLayoutAddCard visibleIf isLogin
            imageCard goneIf isLogin
            textNotLoggedIn goneIf isLogin
            buttonLogin goneIf isLogin
        }
    }

    private fun showDeleteCreditCardDialog() {
        CustomDialog(
            mContext,
            message = getString(R.string.confirm_delete_credit_card),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            positiveButtonClickListener = {
                deleteCreditCard()
            }
        ).show()
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(
            mContext as AppCompatActivity,
            getString(R.string.payment_information)
        )
    }

    private fun backPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.isLoggedIn()) {
                    navigateSafe(R.id.action_paymentInformationFragment_to_profileFragment)
                } else {
                    activity?.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}