package com.yummyfoods.restaurantprofile.models
case class CreateRestaurantInput(restaurantName: String,
                             contactNumber: String,
                             restaurantAddress: List[RestaurantAddress]
                     )
