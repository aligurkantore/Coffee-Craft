package com.example.coffeeapp.ui.fragments.paymentinformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentPaymentInformationBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.util.gone
import com.example.coffeeapp.util.goneIf
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.observeNonNull
import com.example.coffeeapp.util.showMessage
import com.example.coffeeapp.util.visible
import com.example.coffeeapp.util.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentInformationFragment :
    BaseFragment<FragmentPaymentInformationBinding, PaymentInformationViewModel>() {

    private val clickStateMap = mutableMapOf<View, Boolean>()

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
        // backPressed()
        isUserLoggedIn(viewModel.isLoggedIn())
        viewModel.userId?.let { viewModel.creditCardListener(it).equals(true) }
            ?.let { updateCreditCardVisibility(it) }
    }

    override fun setUpListeners() {
        viewBindingScope {
            val clickListeners = mapOf<View, Int>(
                linearLayoutWallet to R.string.pay_from_wallet,
                constraintCreditCard to R.string.pay_from_credit_card,
                linearLayoutGooglePay to R.string.pay_from_google,
                linearLayoutAmazonPay to R.string.pay_from_amazon
            )

            clickListeners.forEach { (view, textResId) ->
                clickStateMap[view] = false

                view.setOnClickListener {
                    val isSelected = clickStateMap[view] ?: false
                    val scale = if (isSelected) 1f else 1.1f
                    view.animate().scaleX(scale).scaleY(scale).setDuration(200).start()
                    clickStateMap[view] = !isSelected

                    clickListeners.keys.filter { it != view }.forEach {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                        clickStateMap[it] = false
                    }
                    if (!isSelected) {
                        textTitlePrice.visible()
                        textPrice.visible()
                        buttonPay.apply {
                            visible()
                            text = mContext.getString(textResId)
                        }
                    } else {
                        textTitlePrice.gone()
                        textPrice.gone()
                        buttonPay.gone()
                    }
                }
            }

            linearLayoutAddCard.setOnClickListener {
                navigateSafe(R.id.action_paymentInformationFragment_to_addCardFragment)
            }

            deleteCreditCart.setOnClickListener {
                showDeleteCreditCardDialog()
            }

            buttonPay.setOnClickListener {
                FireBaseDataManager.moveCartToOrderHistory(mContext)
                navigateSafe(R.id.action_paymentInformationFragment_to_orderFragment)
            }
            showTotalPrice()
        }
    }

    override fun setUpObservers() {
        viewModel.creditCardLiveData.observeNonNull(viewLifecycleOwner) { creditCard ->
            if (creditCard?.userName?.isNotEmpty() == true) {
                viewBindingScope {
                    textCardHolderName.text = creditCard.userName
                    textNumberCreditCard.text = creditCard.cardNumber
                    textExpiryDate.text = creditCard.cardExpiration
                }
                updateCreditCardVisibility(true)
            } else {
                updateCreditCardVisibility(false)
            }


        }
    }

    private fun updateCreditCardVisibility(visible: Boolean) {
        viewBindingScope {
            constraintCreditCard visibleIf visible
            deleteCreditCart visibleIf visible
        }
    }


    private fun deleteCreditCard() {
        FireBaseDataManager.deleteCreditCardData()
        updateCreditCardVisibility(false)
    }

    private fun showTotalPrice() {
        val totalPrice = BaseShared.getString(mContext, "totalPrice", "")
        binding?.textPrice?.text = totalPrice
    }

    private fun isUserLoggedIn(isLogin: Boolean) {
        viewBindingScope {
            constraintCreditCard visibleIf isLogin
            deleteCreditCart visibleIf isLogin
            linearLayoutWallet visibleIf isLogin
            linearLayoutGooglePay visibleIf isLogin
            linearLayoutAmazonPay visibleIf isLogin
            linearLayoutAddCard visibleIf isLogin
            baseEmptyView.apply {
                imageEmpty.setImageResource(R.drawable.add_card)
                textEmpty.text = getString(R.string.must_login)
                buttonAction.text = getString(R.string.login)
            }.also {
                it.apply {
                    constraintBaseEmpty goneIf isLogin
                    buttonAction.setOnClickListener {
                        navigateSafe(R.id.action_paymentInformationFragment_to_loginFragment)
                    }
                }
            }
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
                showMessage(mContext,getString(R.string.credit_card_deleted))
            }
        ).show()
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