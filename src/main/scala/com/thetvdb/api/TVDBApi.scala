package com.thetvdb.api

import com.thetvdb.api.service.ApiService

class TVDBApi(apiKey: String, username: String, userKey: String)
  extends ApiService(apiKey, username, userKey) {
  def this(apiKey: String) = this(apiKey, "", "")
}

object TVDBApi {
  /**
    *
    * @param apiKey API Key
    * @return An API for TVDB
    */
  def apply(apiKey: String): TVDBApi = new TVDBApi(apiKey).buildConnection()

  /**
    *
    * @param apiKey   API Key
    * @param username Username
    * @param userKey  UserKey
    * @return An API for TVDB with user auth
    */
  def apply(apiKey: String, username: String, userKey: String): TVDBApi =
    new TVDBApi(apiKey, username, userKey)
      .buildConnection()
}
