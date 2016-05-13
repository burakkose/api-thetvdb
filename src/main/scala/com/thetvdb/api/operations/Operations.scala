package com.thetvdb.api.operations

import com.thetvdb.api.models.{Language, Series}
import com.thetvdb.api.utils.ApiConfig

import scala.concurrent.Future

trait QueryOperations {
  def getUsers(url: String)

  def getUpdates(url: String, seriesID: Int)

  def getSeries(url: String): Future[List[Series]]

  def getLanguages(url: String): Future[List[Language]]
}

trait ApiOperations extends ApiConfig {
  def getUsers(url: String)

  def getUpdates(url: String, seriesID: Int)

  def getSeries(url: String): Future[List[Series]]

  def getLanguages(url: String): Future[List[Language]]
}