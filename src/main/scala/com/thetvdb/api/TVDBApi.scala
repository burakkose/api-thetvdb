package com.thetvdb.api

import com.thetvdb.api.service.ApiService

class TvDBApi(apiKey: String) extends ApiService(apiKey)

object TvDBApi {
  def apply(apiKey: String): TvDBApi = new TvDBApi(apiKey).buildConnection()
}
