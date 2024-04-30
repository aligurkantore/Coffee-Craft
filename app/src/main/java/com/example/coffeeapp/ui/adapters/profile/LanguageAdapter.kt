package com.example.coffeeapp.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.databinding.ItemLanguageCurrencyBinding
import com.example.coffeeapp.models.profile.Language

class LanguageAdapter(
    private val languageList: List<Language>,
    private val clickLanguage: (Int) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageVH>() {

    inner class LanguageVH(val binding: ItemLanguageCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageVH {
        val view = ItemLanguageCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageVH(view)
    }

    override fun onBindViewHolder(holder: LanguageVH, position: Int) {
        with(holder.binding) {
            val languageData = languageList[position]
            textViewLanguageCurrency.text = languageData.languageName
            linearLanguageCurrency.setOnClickListener { clickLanguage.invoke(position) }
        }
    }

    override fun getItemCount(): Int = languageList.size
}