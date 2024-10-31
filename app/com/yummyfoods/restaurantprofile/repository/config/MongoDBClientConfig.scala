package com.yummyfoods.restaurantprofile.repository.config

import com.google.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.inject.ApplicationLifecycle
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class MongoDBClientConfig @Inject() (config: Configuration,
                                     lifecycle: ApplicationLifecycle) {
  private val mongoDBConfigConfig = MongoDBConfig(config)
  //val mongoDbUri = s"${mongoDBConfigConfig.host}:${mongoDBConfigConfig.port}$urlSeparator${mongoDBConfigConfig.database}$parameterSeparator$mongoDBConfigConfig.authSource"

  val driver = registerDriverShutdownHook(new AsyncDriver()) // first pool

  val database: Future[DB] = for {
    uri <- MongoConnection.fromString(mongoDBConfigConfig.uri)
    con <- driver.connect(uri)
    dn <- Future(uri.db.get)
    db <- con.database(dn)
  } yield db

  def collection: Future[BSONCollection] = database.map(_.collection("restaurants"))

  def registerDriverShutdownHook(mongoDriver: AsyncDriver) = {
    lifecycle.addStopHook { () => {
      println("********************** Shutting down MongoDB ****************************")
      Future(mongoDriver.close())
    } }
    mongoDriver
  }
}