package com.yummyfoods.restaurant.domain

case class Restaurant(restaurantId: String,
                      restaurantName: String,
                      contactNumber: String,
                      restaurantAddress: List[Address],
                      rating: Int,
                      logoUri: String
                     )
