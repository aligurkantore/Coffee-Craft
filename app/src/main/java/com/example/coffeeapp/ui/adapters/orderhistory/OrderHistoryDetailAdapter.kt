package com.example.coffeeapp.ui.adapters.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemOrderHistoryDetailBinding
import com.example.coffeeapp.helper.FireBaseDataManager.userId
import com.example.coffeeapp.models.coffee.CoffeeResponseModel

class OrderHistoryDetailAdapter(
    private var context: Context,
    private var orderHistoryList: List<CoffeeResponseModel>,
) :
    RecyclerView.Adapter<OrderHistoryDetailAdapter.OrderHistoryDetailVH>() {

    inner class OrderHistoryDetailVH(val binding: ItemOrderHistoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderHistoryDetailAdapter.OrderHistoryDetailVH {
        val view =
            ItemOrderHistoryDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return OrderHistoryDetailVH(view)
    }

    override fun onBindViewHolder(
        holder: OrderHistoryDetailAdapter.OrderHistoryDetailVH,
        position: Int,
    ) {
        with(holder.binding) {
            val orderHistoryList = orderHistoryList[position]
            val count = BaseShared.getInt(context, "${userId}/count_${orderHistoryList.id}", 1)

            orderHistoryList.image_link_portrait?.let { imageCoffee.setImageResource(it) }
            textCoffeeName.text = context.getString(R.string.product_name, orderHistoryList.name)
            textCoffeePrice.text =
                context.getString(R.string.price_order_history, orderHistoryList.price.toString())
            textCoffeeQuantity.text = context.getString(R.string.quantity, count.toString())


        }
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }
}