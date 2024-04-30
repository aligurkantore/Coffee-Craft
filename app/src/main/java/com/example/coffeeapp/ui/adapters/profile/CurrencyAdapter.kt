package com.example.coffeeapp.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.databinding.ItemLanguageCurrencyBinding
import com.example.coffeeapp.models.profile.Currency

class CurrencyAdapter(
    private val currencyList: List<Currency>,
    private val clickCurrency: (Int) -> Unit,
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyVH>() {

    inner class CurrencyVH(val binding: ItemLanguageCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyVH {
        val view =
            ItemLanguageCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: CurrencyVH, position: Int) {
        with(holder.binding) {
            val currencyData = currencyList[position]
            textViewLanguageCurrency.text = currencyData.currencyName
            linearLanguageCurrency.setOnClickListener { clickCurrency.invoke(position) }
        }
    }

    override fun getItemCount(): Int = currencyList.size
}