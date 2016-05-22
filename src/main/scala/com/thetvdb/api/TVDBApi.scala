package com.thetvdb.api

import com.thetvdb.api.service.ApiService

class TVDBApi(apiKey: String, username: String, userKey: String)
  extends ApiService(apiKey, username, userKey) {
  def this(apiKey: String) = this(apiKey, "", "")
}

object TVDBApi {
  def apply(apiKey: String): TVDBApi = new TVDBApi(apiKey).buildConnection()

  def apply(apiKey: String, username: String, userKey: String): TVDBApi =
    new TVDBApi(apiKey, username, userKey)
      .buildConnection()
}
