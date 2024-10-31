package com.yummyfoods.userprofile.services

import play.api.libs.json.{JsValue, Json}

object SearchQueryRequestBuilder {
  def restaurantIdQuery(restaurantId: String): JsValue = {
    val query =
      s"""
         |{
         |  "query": {
         |    "match": {
         |      "restaurantId": {
         |        "query" : "$restaurantId"
         |      }
         |    }
         |  }
         |}
         |""".stripMargin
    Json.parse(query)
  }

  def restaurantAddressByIdQuery(restaurantId: String): JsValue = {
    val query =
      s"""
         |{
         |    "_source": {
         |    "include": ["restaurantAddress"]
         |    },
         |  "query": {
         |    "match": {
         |      "restaurantId": {
         |        "query" : "$restaurantId"
         |      }
         |
         |    }
         |  }
         |}
         |""".stripMargin
    Json.parse(query)
  }

}
