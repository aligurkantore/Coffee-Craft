package com.example.coffeeapp.ui.fragments.addcard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentAddCardBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.addCreditCard.AddCreditCard
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.showMessage

class AddCardFragment :
    BaseFragment<FragmentAddCardBinding, AddCardViewModel>() {


    override val viewModelClass: Class<out AddCardViewModel>
        get() = AddCardViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAddCardBinding {
        return FragmentAddCardBinding.inflate(inflater)
    }

    override fun setUpListeners() {
        viewBindingScope {
            buttonAddCard.setOnClickListener {
                val cardName = editTextName.text.toString()
                val cardNumber = editTextCardNumber.text.toString()
                val cardExpirationDate = editTextExpirationDate.text.toString()

                val creditCard = AddCreditCard(cardName, cardNumber, cardExpirationDate)

                if (cardName.isNotEmpty()  && cardNumber.isNotEmpty() && cardExpirationDate.isNotEmpty()) {
                    FireBaseDataManager.addCreditCard(creditCard)
                    navigateSafe(R.id.action_addCardFragment_to_paymentInformationFragment)
                    showMessage(mContext,getString(R.string.credit_card_added))
                } else {
                    showMessage(mContext,getString(R.string.fill_in_all_fields))
                }
            }
        }
    }

    override fun setUpObservers() {}


}