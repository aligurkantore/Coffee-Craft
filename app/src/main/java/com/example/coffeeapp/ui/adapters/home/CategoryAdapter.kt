package com.example.coffeeapp.ui.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ItemCategoryBinding
import com.example.coffeeapp.models.category.Category

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val clickCategory: (Int) -> Unit,
) : RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private var selectedItemPosition: Int = 0


    inner class CategoryVH(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryVH {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryVH(view)
    }

    override fun onBindViewHolder(
        holder: CategoryVH,
        position: Int,
    ) {
        with(holder.binding) {
            val categoryList = categoryList[position]
            textViewCategoryName.text = categoryList.categoryName
            val isSelected = position == selectedItemPosition
            textViewCategoryName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    if (isSelected) R.color.is_selected else R.color.not_selected
                )
            )
            textViewCategoryName.setOnClickListener {
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                clickCategory.invoke(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}