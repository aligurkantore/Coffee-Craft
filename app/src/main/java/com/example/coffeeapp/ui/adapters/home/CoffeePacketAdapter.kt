package com.example.coffeeapp.ui.adapters.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ItemCoffeePacketBinding
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponseItem
import com.example.coffeeapp.util.loadImage

class CoffeePacketAdapter(
    private var context: Context,
    private val coffeePacketList: List<CoffeePacketResponseItem>,
    private val addToCart : (CoffeePacketResponseItem) -> Unit,
    private val addToFavorite : (CoffeePacketResponseItem) -> Unit
) : RecyclerView.Adapter<CoffeePacketAdapter.CoffeePacketVH>() {

    inner class CoffeePacketVH(val binding: ItemCoffeePacketBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeePacketVH {
        val view =
            ItemCoffeePacketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeePacketVH(view)
    }

    override fun getItemCount(): Int {
        return coffeePacketList.size
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: CoffeePacketVH, position: Int) {
        with(holder.binding) {
            val data = coffeePacketList[position]
            data.imageUrl?.let { imageCoffeePacket.loadImage(it) }
            textCoffeePacketName.text = data.name
            textRegion.text = data.region
            val weightInGram = data.weight.toString()
            val formattedWeight = "$weightInGram${context.getString(R.string.gram)}"
            textWeight.text = formattedWeight
            val formattedPrice = context.getString(R.string.price_format, data.price)
            textPrice.text = formattedPrice

            buttonAddToCart.setOnClickListener { addToCart.invoke(data) }
            imageFavorite.setOnClickListener { addToFavorite.invoke(data) }
        }
    }
}