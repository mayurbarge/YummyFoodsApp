package com.yummyfoods.restaurantprofile.models

case class RestaurantAddress(
                             street: String,
                             addressLine1: String,
                             addressLine2: String,
                             city: String,
                             state: String,
                             postalCode: Int,
                             countryCode: String
                           )
