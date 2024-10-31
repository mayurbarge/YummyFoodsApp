package com.yummyfoods.restaurant.admin.config

import com.typesafe.config.ConfigFactory
import com.yummyfoods.restaurant.admin.repository.config.MongoDBConfig
import org.scalatest.Tag
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Injecting
class MongoDBConfigSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {
  override def fakeApplication(): Application = {
    GuiceApplicationBuilder().configure(Map("mongodb.config.uri" -> "mongodb://test-mongodb-uri")).build()
  }

  "MongoDBConfiguration" should {
    "use the mocked connection settings to a database" in {
      val mongoDBConfig = MongoDBConfig(fakeApplication().configuration)
      mongoDBConfig.uri mustBe "mongodb://test-mongodb-uri"
    }
  }
}
