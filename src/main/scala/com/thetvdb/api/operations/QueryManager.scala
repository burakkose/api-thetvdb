package com.thetvdb.api.operations

import com.thetvdb.api.models.{Language, Series}

import scala.concurrent.Future

/**
  * Created by whisper on 5/13/16.
  */
class QueryManager extends QueryOperations {
  override def getUsers(url: String): Unit = ???

  override def getSeries(url: String): Future[List[Series]] = ???

  override def getUpdates(url: String, seriesID: Int): Unit = ???

  override def getLanguages(url: String): Future[List[Language]] = ???
}
