package com.yummyfoods.userprofile.api

import com.yummyfoods.userprofile.api.config.ElasticSearchClientConfig
import play.api.libs.json.JsValue
import play.api.libs.ws._
import play.mvc.Http

import javax.inject.Inject
import scala.concurrent.Future

class ElasticSearchRestClient @Inject() (
                                          ws: WSClient,
                                          config: ElasticSearchClientConfig) {
  import config._
  /*def getConnector = {
    ws.url(
        elasticSearchURL +
          urlSeparator +
          connectorPathSegment)
      .addHttpHeaders(Http.HeaderNames.AUTHORIZATION -> elasticSearchApiKey).get()
  }*/

  val elasticSearchUrlWithAPIKey = ws.url(
      elasticSearchURL +
        urlSeparator +
        searchPathSegment)
    .addHttpHeaders(Http.HeaderNames.AUTHORIZATION -> elasticSearchApiKey)

  def queryByKeyword(keywords: Option[String], offset: Option[Int], limit: Option[Int]): Future[WSResponse] = {
    elasticSearchUrlWithAPIKey
      .withQueryStringParameters(
        (queryParamKeyword, s"${keywords.getOrElse("")}"),
        (queryParamOffset, offset.getOrElse(defaultOffset).toString),
        (queryParamLimit, limit.getOrElse(defaultLimit).toString))
      .get()
  }

  def query(searchQueryJson: JsValue, offset: Option[Int], limit: Option[Int]): Future[WSResponse] = {
    elasticSearchUrlWithAPIKey
      .withQueryStringParameters(
        (queryParamOffset, offset.getOrElse(defaultOffset).toString),
        (queryParamLimit, limit.getOrElse(defaultLimit).toString))
      .post(searchQueryJson)
  }
}
