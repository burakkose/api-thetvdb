package com.thetvdb.api.protocols

import com.thetvdb.api.models.actor.{Actor, ActorResponse}
import com.thetvdb.api.models.auth.{TokenRequest, TokenResponse}
import com.thetvdb.api.models.episode.{Episode, EpisodeResponse}
import com.thetvdb.api.models.language.{Language, LanguageResponse, LanguagesResponse}
import com.thetvdb.api.models.series.{Series, SeriesResponse, SeriesSearchData, SeriesSearchResponse}
import spray.json.DefaultJsonProtocol


object Protocols extends DefaultJsonProtocol {
  implicit val postFormat = jsonFormat1(TokenRequest)
  implicit val tokenFormat = jsonFormat1(TokenResponse)

  implicit val seriesSeearchData = jsonFormat8(SeriesSearchData)
  implicit val seriesSeearch = jsonFormat1(SeriesSearchResponse)
  implicit val seriesFormat = jsonFormat21(Series)
  implicit val seriesResponseFormat = jsonFormat1(SeriesResponse)

  implicit val episodeFormat = jsonFormat22(Episode)
  implicit val episodeRecordDataFormat = jsonFormat1(EpisodeResponse)

  implicit val actorFormat = jsonFormat8(Actor)
  implicit val actorResponseFormat = jsonFormat1(ActorResponse)

  implicit val languageFormat = jsonFormat4(Language)
  implicit val languagesRecordData = jsonFormat1(LanguagesResponse)
  implicit val languageRecordData = jsonFormat1(LanguageResponse)
}
