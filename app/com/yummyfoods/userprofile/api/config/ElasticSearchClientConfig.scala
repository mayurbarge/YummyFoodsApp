package com.yummyfoods.userprofile.api.config

import com.google.inject.Inject
import play.api.Configuration

class ElasticSearchClientConfig @Inject()(config: Configuration) {
  private val elasticSearchConfig = ElasticSearchConfig(config)

  val elasticSearchURL = s"${elasticSearchConfig.baseUri}:${elasticSearchConfig.port}"
  val urlSeparator = "/"
  val connectorPathSegment = "/separator"
  val searchPathSegment = "/_search"
  val elasticSearchApiKey = s"ApiKey ${elasticSearchConfig.appKey}"

  val queryParamKeyword = "q"
  val queryParamOffset = "from"
  val queryParamLimit = "size"

  val defaultOffset = 0
  val defaultLimit = 10
}