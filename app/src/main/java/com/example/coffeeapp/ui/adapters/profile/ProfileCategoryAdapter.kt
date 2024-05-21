package com.example.coffeeapp.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.databinding.ItemProfileCategoryBinding
import com.example.coffeeapp.models.category.Category

class ProfileCategoryAdapter(
    private val categoryList: List<Category>,
    private val clickCategory: ItemClickCategoryListener,
) : RecyclerView.Adapter<ProfileCategoryAdapter.ProfileCategoryVH>() {

    private var selectedCategory: Int? = null

    inner class ProfileCategoryVH(val binding: ItemProfileCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCategoryVH {
        val view =
            ItemProfileCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileCategoryVH(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ProfileCategoryVH, position: Int) {
        with(holder.binding) {
            val profileCategoryList = categoryList[position]
            profileCategoryList.categoryImage?.let { imageCategory.setImageResource(it) }
            textViewCategoryName.text = profileCategoryList.categoryName

            linearProfileCategory.setOnClickListener {
                selectedCategory = holder.adapterPosition
                selectedCategory?.let { selectedPosition ->
                    profileCategoryList.categoryName?.let { categoryName ->
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