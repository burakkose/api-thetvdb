package com.thetvdb.api.utils

import com.typesafe.config.ConfigFactory

private[utils] trait Config {
  protected[utils] val config = ConfigFactory.load()
}

trait BaseConfig extends Config {
  protected val apiConfig = config.getConfig("tvdb")
  protected val BASE = apiConfig.getString("base")
}

trait UserConfig extends BaseConfig {
  private val userConfig = apiConfig.getConfig("tvdb-user")
  protected val USER_URL = BASE + userConfig.getString("user")
  protected val USER_FAVORITES_URL = BASE + userConfig.getString("userFavorites")
  protected val USER_RATINGS_URL = BASE + userConfig.getString("userRatings")
}

trait SeriesConfig extends BaseConfig {
  private val seriesConfig = apiConfig.getConfig("tvdb-series")
  protected val SERIES_URL = BASE + seriesConfig.getString("series")
  protected val SEARCH_SERIES_URL = BASE + seriesConfig.getString("searchSeries")
}

trait LanguageConfig extends BaseConfig {
  private val languageConfig = apiConfig.getConfig("tvdb-language")
  protected val LANGUAGE_URL = BASE + languageConfig.getString("language")
}

trait UpdateConfig extends BaseConfig {
  private val updateConfig = apiConfig.getConfig("tvdb-update")
  protected val UPDATE_URL = BASE + updateConfig.getString("update")
}

trait AuthConfig extends BaseConfig {
  private val authConfig = apiConfig.getConfig("tvdb-auth")
  protected val REFRESH_TOKEN_URL = BASE + authConfig.getString("refreshToken")
  protected val LOGIN_URL = BASE + authConfig.getString("login")
}

trait EpisodeConfig extends BaseConfig {
  private val episodeConfig = apiConfig.getConfig("tvdb-episode")
  protected val EPISODE_URL = BASE + episodeConfig.getString("episode")
}
