package com.example.coffeeapp.ui.adapters.cart

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemCartBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.util.Constants.Companion.L
import com.example.coffeeapp.util.Constants.Companion.M
import com.example.coffeeapp.util.Constants.Companion.S

class CartAdapter(
    private val context: Context,
    private var cartList: MutableList<CoffeeResponseModel>,
    private var navigateToDetail: (CoffeeResponseModel) -> Unit,
    private var totalPriceListener: TotalPriceListener,
    private var deleteListener: (CoffeeResponseModel) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartVH>() {

    inner class CartVH(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartVH(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: CartVH, position: Int) {
        with(holder.binding) {
            val cartData = cartList[position]
            cartData.image_link_portrait?.let { imageCart.setImageResource(it) }
            textNameCoffee.text = cartData.name
            textSpecialIngredientCoffee.text = cartData.special_ingredient

            val selectedSize = BaseShared.getString(context, "size_${cartData.id}", "")
            val price = when (selectedSize) {
                S -> cartData.prices?.firstOrNull { it.size == S }?.price ?: 0.0
                M -> cartData.prices?.firstOrNull { it.size == M }?.price ?: 0.0
                L -> cartData.prices?.firstOrNull { it.size == L }?.price ?: 0.0
                else -> 0.0
            }
            val formattedPrice = context.getString(R.string.price_format, price)
            textPrice.text = formattedPrice
            textSize.text = selectedSize


            val previousCount =
                BaseShared.getInt(context, "count_${cartData.id}", 0)
            if (previousCount <= 0) cartData.count = 1
            else cartData.count = previousCount

            textCount.text = cartData.count.toString()

            imageMinus.setOnClickListener {
                if (cartData.count > 0) {
                    cartData.count--
                    textCount.text = cartData.count.toString()
                    if (cartData.count == 0) {
                        val id = cartData.id
                        FireBaseDataManager.removeFromCart(context, id.toString())
                        cartList.remove(cartData)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }
                BaseShared.saveInt(context, "count_${cartData.id}", cartData.count)
                totalPriceListener.onTotalPriceUpdated(calculateTotalPrice(), cartData.count)
            }
            imagePlus.setOnClickListener {
                cartData.count++
                textCount.text = cartData.count.toString()
                BaseShared.saveInt(context, "count_${cartData.id}", cartData.count)
                totalPriceListener.onTotalPriceUpdated(calculateTotalPrice(), cartData.count)
            }

            constraintCart.setOnClickListener { navigateToDetail.invoke(cartData) }
            imageDelete.setOnClickListener { deleteListener.invoke(cartData)  }
        }
    }

    private fun calculateTotalPrice(): String {
        return cartList.sumByDouble { (it.prices?.first()?.price ?: 0.0) * it.count }.toString()
    }


    interface TotalPriceListener {
        fun onTotalPriceUpdated(totalPrice: String, count: Int)
    }
}