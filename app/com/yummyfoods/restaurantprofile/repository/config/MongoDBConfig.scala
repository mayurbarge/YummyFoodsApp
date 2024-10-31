package com.yummyfoods.restaurantprofile.repository.config

import com.typesafe.config.Config
import play.api.{ConfigLoader, Configuration}

import java.net.URI
case class MongoDBConfig(uri: String)
object MongoDBConfig {
  implicit val configLoader: ConfigLoader[MongoDBConfig] = (rootConfig: Config, path: String) => {
    val config = rootConfig.getConfig(path)
    MongoDBConfig(config.getString(Constants.MONGODB_URI))
  }
  def apply(config: Configuration) = {
    config.get[MongoDBConfig](Constants.MONGODB_CONFIG)
  }
}