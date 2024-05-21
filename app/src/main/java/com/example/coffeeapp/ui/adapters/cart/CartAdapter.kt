package com.example.coffeeapp.ui.adapters.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemCartBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.google.firebase.auth.FirebaseAuth

class CartAdapter(
    private val context: Context,
    private var cartList: MutableList<CoffeeResponseModel>,
    private var navigateToDetail: (CoffeeResponseModel) -> Unit,
    private var totalPriceListener: TotalPriceListener,
    private var deleteListener: (CoffeeResponseModel) -> Unit,
) : RecyclerView.Adapter<CartAdapter.CartVH>() {

    inner class CartVH(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartVH(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        with(holder.binding) {
            val cartList = cartList[position]
            cartList.image_link_portrait?.let { imageCart.setImageResource(it) }
            textNameCoffee.text = cartList.name
            textSpecialIngredientCoffee.text = cartList.special_ingredient
            val formattedPrice =
                context.getString(R.string.price_format, cartList.price)
            textPrice.text = formattedPrice

            val previousCount =
                BaseShared.getInt(context, "${userId}/count_${cartList.id}", 0)
            if (previousCount <= 0) cartList.count = 1
            else cartList.count = previousCount

            textCount.text = cartList.count.toString()

            imageMinus.setOnClickListener {
                if (cartList.count > 0) {
                    cartList.count--
                    textCount.text = cartList.count.toString()
                    if (cartList.count == 0) {
                        val id = cartList.id
                        FireBaseDataManager.removeFromCart(context, id.toString())
                        this@CartAdapter.cartList.remove(cartList)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }
                BaseShared.saveInt(context, "${userId}/count_${cartList.id}", cartList.count)
                totalPriceListener.onTotalPriceUpdated(calculateTotalPrice(), cartList.count)
            }
            imagePlus.setOnClickListener {
                cartList.count++
                textCount.text = cartList.count.toString()
                BaseShared.saveInt(context, "${userId}/count_${cartList.id}", cartList.count)
                totalPriceListener.onTotalPriceUpdated(calculateTotalPrice(), cartList.count)
            }

            constraintCart.setOnClickListener { navigateToDetail.invoke(cartList) }
            imageDelete.setOnClickListener { deleteListener.invoke(cartList) }
        }
    }

    private fun calculateTotalPrice(): String {
        return cartList.sumByDouble { (it.price ?: 0.0) * it.count }.toString()
    }


    interface TotalPriceListener {
        fun onTotalPriceUpdated(totalPrice: String, count: Int)
    }
}