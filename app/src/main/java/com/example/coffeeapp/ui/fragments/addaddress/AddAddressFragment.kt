package com.example.coffeeapp.ui.fragments.addaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeapp.R
import com.example.coffeeapp.base.BaseFragment
import com.example.coffeeapp.databinding.FragmentAddAddressBinding
import com.example.coffeeapp.helper.FireBaseDataManager
import com.example.coffeeapp.models.address.AddAddress
import com.example.coffeeapp.util.navigateSafe
import com.example.coffeeapp.util.showMessage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class AddAddressFragment : BaseFragment<FragmentAddAddressBinding, AddAddressViewModel>(),
    OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override val viewModelClass: Class<out AddAddressViewModel>
        get() = AddAddressViewModel::class.java

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAddAddressBinding {
        return FragmentAddAddressBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun setUpListeners() {
        binding?.apply {
            buttonAddToAddressBook.setOnClickListener {
                val name = editTextName.text.toString()
                val phoneNumber = editTextNumberPhone.text.toString()
                val addressInformation = editTextAddressInformation.text.toString()

                val address = AddAddress("", name, phoneNumber, addressInformation)
                if (name.isNotEmpty() && phoneNumber.isNotEmpty() && addressInformation.isNotEmpty()) {
                    FireBaseDataManager.addAddress(address)
                    navigateSafe(R.id.action_addAddressFragment_to_myAddressesFragment)
                } else showMessage(mContext, getString(R.string.unexpected_error))

            }
        }
    }

    override fun setUpObservers() {}

    override fun onMapReady(googleMap: GoogleMap) {
        /*
        mMap = googleMap
        val exampleLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(exampleLocation).title("Example Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(exampleLocation, 10f))

         */
        showMessage(mContext,"Google Map")
    }
}