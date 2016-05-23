package com.thetvdb.api.operations

import com.thetvdb.api.models.actor.Actor
import com.thetvdb.api.models.auth.TokenResponse
import com.thetvdb.api.models.episode.Episode
import com.thetvdb.api.models.language.Language
import com.thetvdb.api.models.series.Series
import com.thetvdb.api.models.update.Update
import com.thetvdb.api.models.user.{User, UserFavorites, UserRatings}
import com.thetvdb.api.utils._

import scala.concurrent.Future

/**
  * Api Operations
  */
trait ApiOperations extends AuthOperations with LanguageOperations
  with SeriesOperations with EpisodeOperations with UpdateOperation with UserOperation

/**
  * Obtaining and refreshing the JWT token
  */
trait AuthOperations extends AuthConfig {
  /**
    * Returns a session token to be included in the rest of the requests.
    * Note that API key authentication is required for all subsequent requests
    * and user auth is required for routes in the User operations
    *
    * @return
    */
  def buildConnection(): this.type

  /**
    * Refreshes your current, valid JWT token and returns a new token.
    * Hit this route so that you do not have to post to /login with your API key
    * and credentials once you have already been authenticated.
    *
    * @return A new token to use in your subsequent requests
    */
  def refreshToken(): Future[Option[TokenResponse]]
}

/**
  * Available languages and information
  */
trait LanguageOperations extends LanguageConfig {
  /**
    * All available languages.
    *
    * @return An array of language objects.
    */
  def getLanguages(): Future[Option[List[Language]]]

  /**
    * Information about a particular language, given the language ID.
    *
    * @param langID ID of the language
    * @return A language objects.
    */
  def getLanguage(langID: String): Future[Option[Language]]
}

/**
  * Search for a particular series and information about a specific series
  */
trait SeriesOperations extends SeriesConfig {
  /**
    * Allows the user to search for a series based on the following parameters.
    *
    * @param searchKey Name of the series to search for.
    * @return An array of results that match the provided query.
    */
  def searchSeries(searchKey: String): Future[Option[List[Series]]]

  /**
    * Returns a series records that contains all information known about a particular series id.
    *
    * @param id ID of the series
    * @return A series record.
    */
  def getSeries(id: String): Future[Option[Series]]

  /**
    * Returns actors for the given series id
    *
    * @param seriesID ID of the series
    * @return An array of actor objects for the given series id.
    */
  def getActors(seriesID: String): Future[Option[List[Actor]]]


  /**
    * All episodes for a given series.
    *
    * @param seriesID ID of the series
    * @return An array of episode objects for the given series id.
    */
  def getEpisodesBySeriesID(seriesID: String): Future[Option[List[Episode]]]
}

/**
  * Information about a specific episode
  */
trait EpisodeOperations extends EpisodeConfig {
  /**
    * All episodes for a given series.
    *
    * @param episodeID ID of the series
    * @return An array of episode objects for the given series id.
    */
  def getEpisode(episodeID: String): Future[Option[Episode]]
}

/**
  * Series that have been recently updated.
  */
trait UpdateOperation extends UpdateConfig {
  /**
    * Returns an array of series that have changed in a maximum of one week
    * blocks since the provided fromTime.
    * The user may specify a toTime to grab results for less than a week.
    * Any timespan larger than a week will be reduced down to one week automatically.
    *
    * @param fromTime Epoch time to start your date range.
    * @param toTime   Epoch time to end your date range. Must be one week from fromTime.
    * @return An array of Update objects that match the given timeframe.
    */
  def getUpdates(fromTime: String, toTime: Option[String] = None): Future[Option[List[Update]]]
}

/**
  * Routes for handling user data.
  */
trait UserOperation extends UserConfig {
  /**
    * Returns basic information about the currently authenticated user.
    *
    * @return User information.
    */
  def getUser(): Future[Option[User]]

  /**
    * Returns an array of favorite series for a given user,
    * will be a blank array if no favorites exist.
    *
    * @return User favorites.
    */
  def getUserFavorites(): Future[Option[UserFavorites]]

  /**
    * Returns an array of ratings for the given user.
    *
    * @return List of user ratings.
    */
  def getUserRatings(): Future[Option[List[UserRatings]]]

  /**
    * Returns an array of rating of episodes for a given user
    *
    * @return List of user's episodes ratings
    */
  def getUserEpisodeRatings: Future[Option[List[UserRatings]]]

  /**
    * Returns an array of rating of series for a given user
    *
    * @return List of the user's series ratings
    */
  def getUserSeriesRatings: Future[Option[List[UserRatings]]]

  /**
    * Returns an array of rating of banner for a given user
    *
    * @return List of the user's banners ratings
    */
  def getUserBannerRatings: Future[Option[List[UserRatings]]]
}
