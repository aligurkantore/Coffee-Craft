package com.example.coffeeapp.ui.adapters.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ItemMyAddressesBinding
import com.example.coffeeapp.models.address.AddAddress

class MyAddressesAdapter(
    private val addressList: List<AddAddress>,
    private val clickDeleteListener: ClickDeleteListener,
    private val clickItem: (AddAddress) -> Unit,
) : RecyclerView.Adapter<MyAddressesAdapter.MyAddressesVH>() {

    private var selectedItemPosition = RecyclerView.NO_POSITION

    inner class MyAddressesVH(val binding: ItemMyAddressesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAddressesVH {
        val view =
            ItemMyAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAddressesVH(view)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: MyAddressesVH, position: Int) {
        with(holder.binding) {
            val addressList = addressList[position]
            textUserName.text = addressList.name
            textPhoneNumber.text = addressList.phoneNumber
            textAddressInformation.text = addressList.addressInformation

            val isSelected = position == selectedItemPosition
            linearAdress.setBackgroundResource(if (isSelected) R.drawable.background_selected_size else R.drawable.background_default_size)


            cardCoffee.setOnClickListener {
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                clickItem.invoke(addressList)
            }

            imageDelete.setOnClickListener { clickDeleteListener.deleteListener(addressList) }
        }
    }

    interface ClickDeleteListener {
        fun deleteListener(data: AddAddress)
    }

    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }

    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
    }
}