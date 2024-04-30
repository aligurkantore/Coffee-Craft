package com.example.coffeeapp.ui.adapters.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ItemCoffeeBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel

class CoffeeAdapter(
    private var context: Context,
    private var coffeeList : MutableList<CoffeeResponseModel>,
    private var navigateToDetail: (CoffeeResponseModel) -> Unit,
    private var addToCart: (CoffeeResponseModel) -> Unit,
    private var addToFavorite: (CoffeeResponseModel) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeVH>() {

    inner class CoffeeVH(val binding: ItemCoffeeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeVH {
        val view = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeVH(view)
    }

    override fun getItemCount(): Int {
        return coffeeList.size
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: CoffeeVH, position: Int) {
        with(holder.binding){
            val data = coffeeList[position]
            data.image_link_portrait?.let { imageCoffee.setImageResource(it) }
            textCoffeeName.text = data.name
            textCoffeeSpecialIngredient.text = data.special_ingredient
            val formattedPrice = context.getString(R.string.price_format, data.prices?.firstOrNull()?.price)
            textPrice.text = formattedPrice

            contraintCoffee.setOnClickListener { navigateToDetail.invoke(data) }
            imageAddToCart.setOnClickListener { addToCart.invoke(data) }
            imageFavorite.setOnClickListener { addToFavorite.invoke(data) }
        }
    }

    fun updateCoffeeList(newCoffeeList: List<CoffeeResponseModel>) {
        coffeeList.clear()
        coffeeList.addAll(newCoffeeList)
        notifyDataSetChanged()
    }
}