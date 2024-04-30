package com.example.coffeeapp.models.coffeepacket


import com.google.gson.annotations.SerializedName

data class CoffeePacketResponseItem(
    @SerializedName("description")
    val description: String?,
    @SerializedName("flavor_profile")
    val flavorProfile: List<String?>?,
    @SerializedName("grind_option")
    val grindOption: List<String?>?,
    @SerializedName("_id")
    val idString: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("roast_level")
    val roastLevel: Int?,
    @SerializedName("weight")
    val weight: Int?
)