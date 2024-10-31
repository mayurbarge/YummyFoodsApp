package com.yummyfoods.userprofile.validation

import play.api.libs.json.{JsError, JsLookupResult, JsValue, Reads}

object JsonValidationHelper {
  def validateJsValue[A: Reads](json:JsValue) = {
    json.validate[A].asEither.left.map(e => JsError.toJson(e))
  }
  def validateJsLookupResult[A: Reads](json:JsLookupResult) = {
    json.validate[A].asEither.left.map(e => JsError.toJson(e))
  }
}
