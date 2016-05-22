package com.thetvdb.api.utils

import com.typesafe.config.ConfigFactory

private[utils] trait Config {
  protected val config = ConfigFactory.load()
}

trait ApiConfig extends Config {
  private val apiConfig = config.getConfig("api")

  protected val SERIES_URL = apiConfig.getString("series")
  protected val SEARCH_SERIES_URL = apiConfig.getString("searchSeries")
  protected val LANGUAGE_URL = apiConfig.getString("language")
  protected val REFRESH_TOKEN_URL = apiConfig.getString("refreshToken")
  protected val LOGIN_URL = apiConfig.getString("login")
  protected val EPISODE_URL = apiConfig.getString("episode")
  protected val UPDATE_URL = apiConfig.getString("update")
  protected val EPISODES_OF_SERIES_URL = apiConfig.getString("episodesOfSeries")
  protected val USER_URL = apiConfig.getString("user")
  protected val USER_FAVORITES_URL = apiConfig.getString("userFavorites")
  protected val USER_RATINGS_URL = apiConfig.getString("userRatings")
}
