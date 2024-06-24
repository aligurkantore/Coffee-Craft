package com.example.coffeeapp.ui.adapters.orderhistory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ItemOrderHistoryBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.util.formatDate
import com.example.coffeeapp.util.generateRandomKey
import com.google.firebase.auth.FirebaseAuth
import java.util.Date

class OrderHistoryAdapter(
    private var context: Context,
    private var orderHistoryList: List<OrderModel>,
    private var deleteListener: (OrderModel) -> Unit,
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryVH>() {

    inner class OrderHistoryVH(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    val userId = FirebaseAuth.getInstance().currentUser?.uid

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
            val orderHistory = orderHistoryList[position]
            val formattedDate = formatDate(Date(orderHistory.orderDate))

            textOrderId.text = context.getString(R.string.order_id, generateRandomKey(8))
            textOrderDate.text = context.getString(R.string.order_date, formattedDate)

            val count = BaseShared.getInt(
                context,
                "${FireBaseDataManager.userId}/count_${orderHistory.coffeeList.first().id}",
                0
            )
            Log.d("test", "onBindViewHolder: $count")

            val formattedPrice =
                context.getString(R.string.price_format, orderHistory.orderTotal * count)
            textOrderTotal.text = context.getString(R.string.total_price, formattedPrice)

            val orderDetailsAdapter = OrderHistoryDetailAdapter(context, orderHistory.coffeeList)
            recyclerViewOrderHistoryItem.apply {
                adapter = orderDetailsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            imageDelete.setOnClickListener { deleteListener.invoke(orderHistory) }

        }
    }
}
