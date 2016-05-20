package com.thetvdb.api.operations

import com.thetvdb.api.models.actor.Actor
import com.thetvdb.api.models.auth.TokenResponse
import com.thetvdb.api.models.episode.Episode
import com.thetvdb.api.models.language.Language
import com.thetvdb.api.models.series.{Series, SeriesSearchData}

import scala.concurrent.Future

trait ApiOperations extends AuthOperations with LanguageOperations
  with SeriesOperations with ActorOperations with EpisodeOperations

trait AuthOperations {
  /**
    *
    * @return
    */
  def buildConnection(): this.type

  /**
    *
    * @return
    */
  def refreshToken(): Future[TokenResponse]
}

trait LanguageOperations {
  /**
    *
    * @return
    */
  def getLanguages(): Future[List[Language]]

  /**
    *
    * @param langID
    * @return
    */
  def getLanguage(langID: String): Future[Language]
}

trait SeriesOperations {
  /**
    *
    * @param url
    * @return
    */
  def searchSeries(url: String): Future[List[SeriesSearchData]]

  /**
    *
    * @param id
    * @return
    */
  def getSeries(id: String): Future[Series]
}

trait ActorOperations {
  /**
    *
    * @param seriesID
    * @return
    */
  def getActors(seriesID: String): Future[List[Actor]]
}

trait EpisodeOperations {
  /**
    *
    * @param episodeID
    * @return
    */
  def getEpisode(episodeID: String): Future[Episode]
}