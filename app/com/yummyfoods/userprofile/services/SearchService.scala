package com.yummyfoods.userprofile.services

import com.yummyfoods.userprofile.api.ElasticSearchRestClient
import com.yummyfoods.userprofile.models.response.AddressSearchQueryResponse
import com.yummyfoods.userprofile.models.transformers.RestaurantTransformer
import play.api.libs.json.{JsObject, JsValue}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SearchService @Inject() (elasticSearchRestClient: ElasticSearchRestClient) {
  def searchByKeyword(keywords: Option[String], offset: Option[Int], limit: Option[Int]) = {
    elasticSearchRestClient.queryByKeyword(keywords, offset, limit)
      .map(response => RestaurantTransformer.readRestaurants(response.json))
  }
  def searchByName(searchQueryJson: JsValue, offset: Option[Int], limit: Option[Int]) = {
    elasticSearchRestClient.query(searchQueryJson, offset, limit)
      .map(response => RestaurantTransformer.readRestaurants(response.json))
  }

  def searchRestaurantByID(restaurantId: String) = {
    elasticSearchRestClient.query(SearchQueryRequestBuilder.restaurantIdQuery(restaurantId), None, None)
      .map(response => RestaurantTransformer.readRestaurants(response.json))
  }

  def searchRestaurantAddressByID(restaurantId: String): Future[Either[JsObject, AddressSearchQueryResponse]] = {
    elasticSearchRestClient.query(SearchQueryRequestBuilder.restaurantAddressByIdQuery(restaurantId), None, None)
      .map(response => RestaurantTransformer.readAddresses(response.json))
  }
}
