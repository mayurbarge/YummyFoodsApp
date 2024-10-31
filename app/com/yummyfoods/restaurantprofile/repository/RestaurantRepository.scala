package com.yummyfoods.restaurantprofile.repository

import com.google.inject.Inject
import com.yummyfoods.restaurant.domain.Restaurant
import com.yummyfoods.restaurantprofile.repository.config.MongoDBClientConfig

import scala.concurrent.ExecutionContext.Implicits.global

class RestaurantRepository @Inject()(mongoDBClientConfig: MongoDBClientConfig) {
  def createRestaurant(restaurant: Restaurant) = {
    import com.yummyfoods.restaurantprofile.models.transformer.RestaurantSchemaTransformer._
    mongoDBClientConfig.collection.flatMap(_.insert.one(restaurant))
  }
}
