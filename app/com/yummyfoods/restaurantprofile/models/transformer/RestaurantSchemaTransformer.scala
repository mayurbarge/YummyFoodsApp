package com.yummyfoods.restaurantprofile.models.transformer

import com.yummyfoods.restaurant.domain.{Address, Restaurant}
import reactivemongo.api.bson.{BSONDocumentWriter, Macros}

object RestaurantSchemaTransformer {
  implicit def addressWriter: BSONDocumentWriter[Address] = Macros.writer[Address]
  implicit def restaurantWriter: BSONDocumentWriter[Restaurant] = Macros.writer[Restaurant]
}