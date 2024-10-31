package com.yummyfoods.userprofile.controllers

import com.yummyfoods.userprofile.services.SearchService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{BaseController, ControllerComponents, Request}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class SearchController @Inject()(
                                       searchService: SearchService,
                                       val controllerComponents: ControllerComponents) extends BaseController {
  def searchByKeywords(keywords: Option[String], offset: Option[Int], limit: Option[Int]) = Action.async {
    import com.yummyfoods.userprofile.models.transformers.SearchQueryResponseTransformer._

    searchService.searchByKeyword(keywords, offset, limit)
      .map {
        case Left(error) => InternalServerError("Error while parsing result: " + error)
        case Right(result) => Ok(Json.toJson(result))
    }
  }

  def query(offset: Option[Int], limit: Option[Int]) = Action.async(parse.json) { request: Request[JsValue] =>
    import com.yummyfoods.userprofile.models.transformers.SearchQueryResponseTransformer._

    searchService.searchByName(request.body, offset, limit).map {
      case Left(error) => InternalServerError("Error while parsing result: " + error)
      case Right(result) => Ok(Json.toJson(result))
    }
  }
  def getRestaurantByID(restaurantId: String) = Action.async {
    import com.yummyfoods.userprofile.models.transformers.SearchQueryResponseTransformer._

    searchService.searchRestaurantByID(restaurantId).map {
      case Left(error) => InternalServerError("Error while parsing result: " + error)
      case Right(result) => Ok(Json.toJson(result))
    }
  }

  def getAddressByRestaurantID(restaurantId: String) = Action.async {
    import com.yummyfoods.userprofile.models.transformers.SearchQueryResponseTransformer._

    searchService.searchRestaurantAddressByID(restaurantId).map {
      case Left(error) => InternalServerError("Error while parsing result: " + error)
      case Right(result) => Ok(Json.toJson(result))
    }
  }

}
