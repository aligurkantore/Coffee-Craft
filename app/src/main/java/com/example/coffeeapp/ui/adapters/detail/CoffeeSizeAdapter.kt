package com.example.coffeeapp.ui.adapters.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemSizeBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel

class CoffeeSizeAdapter(
    private val sizeList: List<CoffeeResponseModel.CoffeePrice>,
    private val clickListener: ItemClickListener,
) : RecyclerView.Adapter<CoffeeSizeAdapter.CoffeeSizeVH>() {

    private var selectedItemPosition = 0

    inner class CoffeeSizeVH(val binding: ItemSizeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CoffeeSizeAdapter.CoffeeSizeVH {
        val view = ItemSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeSizeVH(view)
    }

    override fun onBindViewHolder(holder: CoffeeSizeAdapter.CoffeeSizeVH, position: Int) {
        val size = sizeList[position].size
        with(holder.binding) {
            textSize.text = size ?: ""
            val isSelected = position == selectedItemPosition
            textSize.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    if (isSelected) R.color.is_selected else R.color.white
                )
            )

            linearLayoutItem.setBackgroundResource(
                if (isSelected) R.drawable.background_selected_size
                else R.drawable.background_default_size
            )

            linearLayoutItem.setOnClickListener {
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                if (size != null) {
                    clickListener.onSizeClicked(size)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }


    interface ItemClickListener {
        fun onSizeClicked(size: String)
    }
}