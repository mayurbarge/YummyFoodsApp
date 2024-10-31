package com.yummyfoods.userprofile.services.config

import com.typesafe.config.ConfigFactory
import com.yummyfoods.userprofile.api.config.ElasticSearchConfig
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration}

class ElasticSearchConfigSpec extends PlaySpec with GuiceOneAppPerTest {
  override def fakeApplication(): Application = {
    val configuration = Configuration(ConfigFactory.load("application.test.conf"))
    GuiceApplicationBuilder().configure(configuration).build()
  }

  "ElasticSearchConfig" should {
    "use the mocked connection settings to a database" in {
      val elasticSearchConfig = ElasticSearchConfig(fakeApplication().configuration)
      elasticSearchConfig.baseUri.toASCIIString mustBe "http://testurl"
      elasticSearchConfig.port mustBe 9200
      elasticSearchConfig.appKey mustBe "test-api-key"
    }
  }
}
