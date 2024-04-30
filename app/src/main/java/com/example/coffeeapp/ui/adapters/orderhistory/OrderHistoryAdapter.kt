package com.example.coffeeapp.ui.adapters.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ItemOrderHistoryBinding
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.util.ObjectUtil
import com.example.coffeeapp.util.formatDate
import java.util.Date

class OrderHistoryAdapter(
    private var context: Context,
    private var orderHistoryList: List<CoffeeResponseModel>,
    private var deleteListener: (CoffeeResponseModel) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryVH>() {

    inner class OrderHistoryVH(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryVH {
        val view =
            ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryVH(view)
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryVH, position: Int) {
        with(holder.binding) {
            val data = orderHistoryList[position]
            val orderIdText = context.getString(R.string.order_id, ObjectUtil.generateOrderId())
            val currentDate = Date()
            val formattedDate = formatDate(currentDate)
            val orderDateText = context.getString(R.string.order_date, formattedDate)
            val productNameText = context.getString(R.string.product_name, data.name)

            data.image_link_portrait?.let { imageCoffee.setImageResource(it) }
            textOrderId.text = orderIdText
            textOrderDate.text = orderDateText
            textCoffeeName.text = productNameText

            imageDelete.setOnClickListener { deleteListener.invoke(data) }

        }
    }
}