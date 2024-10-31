package com.yummyfoods.restaurantprofile.models.transformer

import com.yummyfoods.restaurantprofile.models.{CreateRestaurantInput, RestaurantAddress}
import play.api.libs.json.{Json, OFormat}

object RestaurantJsonTransformer {
  implicit val addressInputFormat: OFormat[RestaurantAddress] = Json.format[RestaurantAddress]
  implicit val restaurantInputFormat: OFormat[CreateRestaurantInput] = Json.format[CreateRestaurantInput]
}
