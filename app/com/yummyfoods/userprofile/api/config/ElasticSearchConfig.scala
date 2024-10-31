package com.yummyfoods.userprofile.api.config

import com.typesafe.config.Config
import play.api.{ConfigLoader, Configuration}

import java.net.URI

case class ElasticSearchConfig(baseUri: URI, port: Int, appKey: String)

object ElasticSearchConfig {

  implicit val configLoader: ConfigLoader[ElasticSearchConfig] = new ConfigLoader[ElasticSearchConfig] {
    def load(rootConfig: Config, path: String): ElasticSearchConfig = {
      val config = rootConfig.getConfig(path)
      ElasticSearchConfig(
        baseUri = new URI(config.getString(Constants.ELASTICSEARCH_URI)),
        port = config.getInt(Constants.ELASTICSEARCH_PORT),
        appKey = config.getString(Constants.ELASTICSEARCH_API_KEY)
      )
    }
  }

  def apply(config: Configuration) = {
    config.get[ElasticSearchConfig](Constants.ELASTICSEARCH_CONFIG)
  }
}