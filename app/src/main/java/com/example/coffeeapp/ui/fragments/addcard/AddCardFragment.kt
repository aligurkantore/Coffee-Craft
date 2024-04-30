package com.example.coffeeapp.ui.fragments.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.FragmentAddCardBinding
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.Constants.Companion.CARDNAME
import com.example.coffeeapp.util.Constants.Companion.CARDNUMBER
import com.example.coffeeapp.util.Constants.Companion.CARDEXPIRATIONDATE
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonAddCard.setOnClickListener {
                val cardName = editTextName.text.toString()
                val cardNumber = editTextCardNumber.text.toString()
                val cardExpirationDate = editTextExpirationDate.text.toString()

                if (cardName.isNotEmpty()  && cardNumber.isNotEmpty() && cardExpirationDate.isNotEmpty()) {
                    navigateSafe(R.id.action_addCardFragment_to_paymentInformationFragment)
                } else {
                    showMessage(mContext,getString(R.string.fill_in_all_fields))
                }
                BaseShared.saveString(mContext,CARDNAME,cardName)
                BaseShared.saveString(mContext, CARDNUMBER,cardNumber)
                BaseShared.saveString(mContext, CARDEXPIRATIONDATE,cardExpirationDate)
            }
        }
    }

    override fun setUpObservers() {
    }

    private fun setUpAppBar() {
        ObjectUtil.updateAppBarTitle(
            mContext as AppCompatActivity,
            getString(R.string.add_card)
        )
    }

}