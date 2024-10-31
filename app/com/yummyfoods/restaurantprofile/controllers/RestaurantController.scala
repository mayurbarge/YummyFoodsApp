package com.yummyfoods.restaurantprofile.controllers

import com.yummyfoods.restaurantprofile.models.CreateRestaurantInput
import com.yummyfoods.restaurantprofile.services.RestaurantAdminService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RestaurantController @Inject()(
                                           restaurantAdminService: RestaurantAdminService,
                                           val controllerComponents: ControllerComponents) extends BaseController {
  def create(): Action[JsValue] = Action.async(parse.json) { implicit request => {

    import com.yummyfoods.restaurantprofile.models.transformer.RestaurantJsonTransformer._

      request.body.validate[CreateRestaurantInput].fold(
        _ => Future.successful(BadRequest("Cannot parse request body")),
        restaurant => {
          restaurantAdminService.createRestaurant(restaurant).map {
            _ => Created(Json.toJson(restaurant))
          }
        }
      )
    }
  }
}
