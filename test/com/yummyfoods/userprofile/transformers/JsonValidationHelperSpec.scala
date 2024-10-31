package com.yummyfoods.userprofile.transformers

import com.yummyfoods.restaurant.domain.{Address, Restaurant}
import com.yummyfoods.userprofile.validation.JsonValidationHelper
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json

import scala.io.Source
class JsonValidationHelperSpec extends PlaySpec {

  "JsonValidationHelper" should {
    "correctly validate an Address Json value" in {
      new TestData {
        val expectedResult = Address("66e1a68d8f1bd53dc0cc50ea",
          "East Street", "MG Road", "Camp", "Pune", "Mah", 1111, "IN")
        JsonValidationHelper.validateJsValue[Address](validJson).toOption.get mustBe expectedResult
      }
    }

    "give path missing error result for invalid Address" in {
      new TestData {
        val actualResult = JsonValidationHelper.validateJsValue[Address](invalidJson)
        actualResult.isLeft mustBe true
        actualResult.left.toString.contains("error.path.missing") mustBe true
      }
    }

    "correctly validate an Restaurant Json value" in {
      new TestData {
        val expectedAddress = Address("address-id-2", "West Street", "Back of MG Road", "Cantonment", "Pune", "Mah", 1111, "IN")
        val expectedRestaurant = Restaurant("rest-id-1", "MacDonalds", "9100", List(expectedAddress), 1, "")

        JsonValidationHelper.validateJsValue[Restaurant](validRestaurantJson).toOption.get mustBe expectedRestaurant
      }
    }

    "give path missing error result for invalid Restaurant" in {
      new TestData {
        val actualResult = JsonValidationHelper.validateJsValue[Address](invalidJson)
        actualResult.isLeft mustBe true
        actualResult.left.toString.contains("error.path.missing") mustBe true
      }
    }

    "correctly parse hits count for given Json" in {
      new TestData {
        val actualResult = JsonValidationHelper.validateJsLookupResult[Int](validElasticSearchResponse \ "hits" \ "total" \ "value")
        actualResult mustBe Right(1)
      }
    }

    "give error when hit count for invalid hit count" in {
      new TestData {
        val actualResult = JsonValidationHelper.validateJsLookupResult[Int](validElasticSearchResponse \ "hits" \ "total")
        actualResult.isLeft mustBe true
      }
    }
  }
  trait TestData {
    val validInputFile = Source.fromFile("./test/test-data/single-restaurant-sample.json")
    val validJson = Json.parse(validInputFile.mkString)

    val invalidInputFile = Source.fromFile("./test/test-data/single-restaurant-invalid-sample.json")
    val invalidJson = Json.parse(invalidInputFile.mkString)

    val validRestaurantInputFile = Source.fromFile("./test/test-data/valid-restaurant.json")
    val validRestaurantJson = Json.parse(validRestaurantInputFile.mkString)

    val invalidRestaurantInputFile = Source.fromFile("./test/test-data/invalid-restaurant.json")
    val invalidRestaurantJson = Json.parse(invalidRestaurantInputFile.mkString)

    val validElasticSearchInputFile = Source.fromFile("./test/test-data/valid-elastic-search-response.json")
    val validElasticSearchResponse = Json.parse(validElasticSearchInputFile.mkString)
  }
}
