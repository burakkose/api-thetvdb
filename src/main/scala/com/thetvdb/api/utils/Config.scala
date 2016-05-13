package com.thetvdb.api.utils

import com.typesafe.config.ConfigFactory

private[utils] trait Config {
  protected val config = ConfigFactory.load()
}

trait ApiConfig extends Config {
  private val apiConfig = config.getConfig("http")
}
