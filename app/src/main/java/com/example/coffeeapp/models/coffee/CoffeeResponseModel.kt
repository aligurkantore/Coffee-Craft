package com.example.coffeeapp.models.coffee

import java.io.Serializable

data class CoffeeResponseModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val roasted: String? = null,
    val image_link_portrait: Int? = null,
    val ingredients: String? = null,
    val special_ingredient: String? = null,
    val prices: List<CoffeePrice>? = null,
    val average_rating: Double? = null,
    val ratings_count: String? = null,
    val type: String? = null,
) : Serializable {

    var count: Int = 1

    data class CoffeePrice(
        val size: String? = null,
        val price: Double? = null,
        val currency: String? = null,
    )
}
