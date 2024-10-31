package com.yummyfoods.userprofile.models.transformers

import com.yummyfoods.userprofile.models.response.{AddressSearchQueryResponse, SearchQueryResponse}

object SearchQueryResponseTransformer {
  import play.api.libs.json._
  import RestaurantTransformer._

  implicit val searchQueryResponseWrites: OWrites[SearchQueryResponse] = Json.writes[SearchQueryResponse]
  implicit val searchQueryResponseReads: Reads[SearchQueryResponse] = Json.reads[SearchQueryResponse]

  implicit val addressSearchQueryResponseWrites: OWrites[AddressSearchQueryResponse] = Json.writes[AddressSearchQueryResponse]
  implicit val addressSearchQueryResponseReads: Reads[AddressSearchQueryResponse] = Json.reads[AddressSearchQueryResponse]
}
