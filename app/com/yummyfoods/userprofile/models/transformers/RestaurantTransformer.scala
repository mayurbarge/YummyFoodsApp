package com.yummyfoods.userprofile.models.transformers

import cats.implicits.toTraverseOps
import com.yummyfoods.restaurant.domain.{Address, Restaurant}
import com.yummyfoods.userprofile.models.response.{AddressSearchQueryResponse, SearchQueryResponse}
import play.api.libs.functional.syntax._
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json._

object RestaurantTransformer {
  implicit val addressReads: Reads[Address] = (
    (JsPath \ "addressId").read[String] and
      (JsPath \ "street").read[String] and
      (JsPath \ "addressLine1").read[String] and
      (JsPath \ "addressLine2").read[String] and
      (JsPath \ "city").read[String] and
      (JsPath \ "state").read[String] and
      (JsPath \ "postalCode").read[Int] and
      (JsPath \ "countryCode").read[String]
    )(Address.apply _)
  implicit val addressWrites: OWrites[Address] = Json.writes[Address]

  implicit val restaurantReads: Reads[Restaurant] = (
    (JsPath \ "restaurantId").read[String] and
      (JsPath \ "restaurantName").read[String] and
      (JsPath \ "contactNumber").read[String] and
      (JsPath \ "restaurantAddress").read[List[Address]] and
      (JsPath \ "rating").read[Int] and
      (JsPath \ "logoUri").read[String]
    )(Restaurant.apply _)

  implicit val restaurantWrites: OWrites[Restaurant] = Json.writes[Restaurant]

  def readRestaurants(json: JsValue) = {
    import com.yummyfoods.userprofile.validation.JsonValidationHelper._

    val validatedHits = validateJsLookupResult[Int]((json \ "hits" \ "total" \ "value"))
    val validatedRestaurantsList = (json \\ "_source").map(validateJsValue[Restaurant](_)).toList.sequence
    for {
      hits <- validatedHits
      restaurants <- validatedRestaurantsList
    } yield {
      SearchQueryResponse(hits, restaurants)
    }
  }

  def readAddresses(json: JsValue) = {
    import com.yummyfoods.userprofile.validation.JsonValidationHelper._
    val validatedHits: Either[JsObject, Int] = validateJsLookupResult[Int]((json \ "hits" \ "total" \ "value"))
    val validatedAddressList = (json \\ "restaurantAddress").map(validateJsValue[Seq[Address]](_)).toList.sequence

    for {
      hits <- validatedHits
      addresses <- validatedAddressList
    } yield {
      AddressSearchQueryResponse(hits, addresses.flatMap(_.toList))
    }
  }
}