package com.example.coffeeapp.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.databinding.ItemPersonelInformationBinding
import com.example.coffeeapp.models.category.Category

class PersonelInformationAdapter(
    private val personelData: List<Category>,
    private val clickCategory: ItemClickCategoryListener,
) :
    RecyclerView.Adapter<PersonelInformationAdapter.PersonelInformationVH>() {

    private var selectedCategory: Int? = null

    inner class PersonelInformationVH(val binding: ItemPersonelInformationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonelInformationVH {
        val view = ItemPersonelInformationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonelInformationVH(view)
    }

    override fun getItemCount(): Int {
        return personelData.size
    }

    override fun onBindViewHolder(holder: PersonelInformationVH, position: Int) {
        with(holder.binding) {
            val data = personelData[position]
            textViewPersonelInformation.text = data.categoryName

            constraintPersonelCategory.setOnClickListener {
                selectedCategory = holder.adapterPosition
                selectedCategory?.let { selectedPosition ->
                    data.categoryName?.let { categoryName ->
                        clickCategory.onClickListener(
                            categoryName,
                            selectedPosition
                        )
                    }
                }
            }
        }
    }

    interface ItemClickCategoryListener {
        fun onClickListener(categoryName: String, position: Int)
    }
}