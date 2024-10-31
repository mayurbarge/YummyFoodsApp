package com.yummyfoods.userprofile.transformers

import com.yummyfoods.restaurant.domain.{Address, Restaurant}
import com.yummyfoods.userprofile.models.response.{AddressSearchQueryResponse, SearchQueryResponse}
import com.yummyfoods.userprofile.models.transformers.RestaurantTransformer
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}

import scala.io.Source

class RestaurantTransformerSpec extends PlaySpec {

  "Restaurant Transformer" must {
    "correctly parse address from the json to Address search query result" in {
      new TestData {
        val expectedResult =
          AddressSearchQueryResponse(1,
            List(Address("address-id-1", "FC Road", "Shivajinagar", "Shivajinagar Annex", "Pune", "Mah", 11, "IN")))

        val actualResult = RestaurantTransformer.readAddresses(validAddressJson).toOption.get
        actualResult.hits mustBe expectedResult.hits
        actualResult.addresses mustBe expectedResult.addresses
      }
    }

    "return error while parsing incorrect address from the json to Address search query result" in {
      new TestData {
        val actualResult: Either[JsObject, AddressSearchQueryResponse] = RestaurantTransformer.readAddresses(invalidJson)

        actualResult.isLeft mustBe true
        actualResult.left.toString.contains("'hits' is undefined") mustBe true
      }
    }

    "correctly parse Restaurant from the json to Restaurant search query result" in {
      new TestData {
        val expectedResult = {
          SearchQueryResponse(1, List(
            Restaurant("res-1", "MacD", "919",
              List(Address("address-id-2", "Baker Street", "Lane 1", "Hampshire", "London", "ES", 123, "UK")), 1, ""))
          )
        }

        val actualResult = RestaurantTransformer.readRestaurants(validRestaurantJson).toOption.get
        actualResult.hits mustBe expectedResult.hits
        actualResult.restaurants mustBe expectedResult.restaurants
      }
    }

    "return error while parsing incorrect restaurant from the json to Restaurant search query result" in {
      new TestData {
        val actualResult = RestaurantTransformer.readRestaurants(invalidJson)

        actualResult.isLeft mustBe true
        actualResult.left.toString.contains("'hits' is undefined") mustBe true
      }
    }

  }

  trait TestData {
    val validAddressInputFile = Source.fromFile("./test/test-data/valid-address-search-response.json")
    val validAddressJson = Json.parse(validAddressInputFile.mkString)

    val validRestaurantInputFile = Source.fromFile("./test/test-data/valid-restaurant-search-response.json")
    val validRestaurantJson = Json.parse(validRestaurantInputFile.mkString)

    val invalidInputFile = Source.fromFile("./test/test-data/invalid-restaurant.json")
    val invalidJson = Json.parse(invalidInputFile.mkString)
  }

}
