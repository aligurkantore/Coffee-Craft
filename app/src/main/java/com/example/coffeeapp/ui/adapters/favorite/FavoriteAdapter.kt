package com.example.coffeeapp.ui.adapters.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.databinding.ItemFavoriteBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel

class FavoriteAdapter(
    private var favoriteList: List<CoffeeResponseModel>,
    private var navigateToDetail: (CoffeeResponseModel) -> Unit,
    private var removeFromFavorites: (CoffeeResponseModel) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteVH>() {

    inner class FavoriteVH(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteVH(view)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
        with(holder.binding) {
            val favoriteList = favoriteList[position]
            favoriteList.image_link_portrait?.let { imageCoffee.setImageResource(it) }
            textNameCoffee.text = favoriteList.name
            textSpecialIngredientCoffee.text = favoriteList.special_ingredient
            textAverageRating.text = favoriteList.average_rating.toString()
            textRatingsCount.text = favoriteList.ratings_count
            textDescription.text = favoriteList.description

            constraintFavorite.setOnClickListener { navigateToDetail.invoke(favoriteList) }
            imageFavorite.setOnClickListener { removeFromFavorites.invoke(favoriteList) }
        }
    }
}