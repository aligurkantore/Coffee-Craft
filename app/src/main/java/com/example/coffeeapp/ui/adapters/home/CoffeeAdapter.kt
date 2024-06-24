package com.example.coffeeapp.ui.adapters.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemCoffeeBinding
import com.example.coffeeapp.helper.FireBaseDataManager.userId
import com.example.coffeeapp.models.coffee.CoffeeResponseModel

class CoffeeAdapter(
    private var context: Context,
    private var coffeeList: MutableList<CoffeeResponseModel>,
    private var navigateToDetail: (CoffeeResponseModel, (String)) -> Unit,
    private var addToCart: (CoffeeResponseModel) -> Unit,
    private val toggleFavorite: (CoffeeResponseModel, Boolean) -> Unit,
    private val recyclerViewType: String,
    private val isLoggedIn: Boolean,
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeVH>() {

    inner class CoffeeVH(val binding: ItemCoffeeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeVH {
        val view = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeVH(view)
    }

    override fun getItemCount(): Int {
        return coffeeList.size
    }

    override fun onBindViewHolder(holder: CoffeeVH, position: Int) {
        with(holder.binding) {
            val coffeeList = coffeeList[position]
            coffeeList.image_link_portrait?.let { imageCoffee.setImageResource(it) }
            textCoffeeName.text = coffeeList.name
            textCoffeeSpecialIngredient.text = coffeeList.special_ingredient
            val formattedPrice =
                context.getString(R.string.price_format, coffeeList.price)
            textPrice.text = formattedPrice

            contraintCoffee.setOnClickListener {
                navigateToDetail.invoke(
                    coffeeList,
                    recyclerViewType
                )
            }
            imageAddToCart.setOnClickListener { addToCart.invoke(coffeeList) }

            val isFavorite =
                if (isLoggedIn) {
                    BaseShared.getBoolean(context, "${userId}/favorite_${coffeeList.id}", false)
                } else false
            imageFavorite.setImageResource(if (isFavorite) R.drawable.select_favorite_heart else R.drawable.heart)

            imageFavorite.setOnClickListener {
                val previousFavorite = !isFavorite
                BaseShared.saveBoolean(
                    context,
                    "${userId}/favorite_${coffeeList.id}",
                    previousFavorite
                )
                notifyItemChanged(position)
                if (isLoggedIn) toggleFavorite.invoke(coffeeList, true)
                else toggleFavorite.invoke(coffeeList, false)
                updateFavoriteState(coffeeList.id, holder)
            }

            updateFavoriteState(coffeeList.id, holder)
        }
    }

    private fun updateFavoriteState(id: String?, holder: CoffeeVH) {
        val isFavorite = BaseShared.getBoolean(
            context,
            "${userId}/favorite_$id",
            false
        )
        holder.binding.imageFavorite.setImageResource(
            if (isFavorite) R.drawable.select_favorite_heart else R.drawable.heart
        )

        Log.d("agt", "updateFavoriteState: $isFavorite")
    }

    fun updateCoffeeList(newCoffeeList: List<CoffeeResponseModel>) {
        coffeeList.clear()
        coffeeList.addAll(newCoffeeList)
        notifyDataSetChanged()
    }
}