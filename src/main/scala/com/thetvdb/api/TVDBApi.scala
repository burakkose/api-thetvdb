package com.thetvdb.api

import com.thetvdb.api.models.{Language, Series}
import com.thetvdb.api.operations.ApiOperations

import scala.concurrent.Future

class TVDBApi(apiKey: String) extends ApiOperations {
  override def getUsers(url: String): Unit = ???

  override def getSeries(url: String): Future[List[Series]] = ???

  override def getUpdates(url: String, seriesID: Int): Unit = ???

  override def getLanguages(url: String): Future[List[Language]] = ???
}

