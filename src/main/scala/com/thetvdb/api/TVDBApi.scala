package com.thetvdb.api

import com.thetvdb.api.service.ClientService

class TvDBApi(apiKey: String) extends ClientService(apiKey)

object TvDBApi {
  def apply(apiKey: String): TvDBApi = new TvDBApi(apiKey).buildConnection()
}
