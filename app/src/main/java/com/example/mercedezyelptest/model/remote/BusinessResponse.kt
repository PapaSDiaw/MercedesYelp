package com.example.mercedezyelptest.model.remote

data class BusinessResponse(
    val businesses: List<Business>
)

data class Business(
    val name: String,
    val price: String,
    val image_url: String,
    val rating: Int,
    val distance: Float,
    val display_phone: String,
    val location: BusinessLocation
)

data class BusinessLocation(
    val display_address: String
)
