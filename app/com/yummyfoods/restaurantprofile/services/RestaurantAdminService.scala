package com.yummyfoods.restaurantprofile.services

import com.google.inject.Inject
import com.yummyfoods.restaurant.domain.{Address, Restaurant}
import com.yummyfoods.restaurantprofile.models.CreateRestaurantInput
import com.yummyfoods.restaurantprofile.repository.RestaurantRepository

import java.util.UUID

class RestaurantAdminService @Inject()(restaurantRepository: RestaurantRepository) {
  def createRestaurant(restaurantInput: CreateRestaurantInput) = {
    import com.yummyfoods.restaurantprofile.models.transformer.RestaurantJsonTransformer._
    restaurantRepository.createRestaurant(convertInputModeltoDomain(restaurantInput))
  }

  def convertInputModeltoDomain(restaurantInput: CreateRestaurantInput) = {
    import restaurantInput._
    Restaurant(
      generateUUID, restaurantName, contactNumber,
      restaurantAddress.map(addressInput => {
        import addressInput._
        Address(generateUUID, street, addressLine1, addressLine2, city,
          state, postalCode, countryCode)
      }),
      0,
      ""
    )
  }

  def generateUUID = UUID.randomUUID().toString
}
