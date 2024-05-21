package com.example.coffeeapp.ui.adapters.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemLanguageCurrencyBinding
import com.example.coffeeapp.models.profile.Language
import com.example.coffeeapp.util.Constants.Companion.SELECTED_LANGUAGE_POSITION

class LanguageAdapter(
    private var context: Context,
    private val languageList: List<Language>,
    private val clickLanguage: (Int) -> Unit,
) : RecyclerView.Adapter<LanguageAdapter.LanguageVH>() {

    private var selectedItemPosition: Int =
        BaseShared.getInt(context, SELECTED_LANGUAGE_POSITION, 0)

    inner class LanguageVH(val binding: ItemLanguageCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageVH {
        val view =
            ItemLanguageCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageVH(view)
    }

    override fun onBindViewHolder(holder: LanguageVH, position: Int) {
        with(holder.binding) {
            val languageList = languageList[position]
            textViewLanguageCurrency.text = languageList.languageName
            val isSelected = position == selectedItemPosition
            imageArrow.setImageResource(if (isSelected) R.drawable.ic_right_arrow else 0)

            linearLanguageCurrency.setOnClickListener {
                if (selectedItemPosition != position) {
                    val previousSelectedItemPosition = selectedItemPosition
                    selectedItemPosition = holder.adapterPosition
                    BaseShared.saveInt(context, SELECTED_LANGUAGE_POSITION, selectedItemPosition)
                    notifyItemChanged(previousSelectedItemPosition)
                    notifyItemChanged(selectedItemPosition)
                    clickLanguage.invoke(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = languageList.size
}