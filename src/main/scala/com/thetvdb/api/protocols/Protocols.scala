package com.thetvdb.api.protocols

import com.thetvdb.api.models.actor.{Actor, ActorResponse}
import com.thetvdb.api.models.auth.{TokenRequest, TokenResponse}
import com.thetvdb.api.models.episode.{Episode, EpisodeResponse, SeriesEpisodesResponse}
import com.thetvdb.api.models.language.{Language, LanguageResponse, LanguagesResponse}
import com.thetvdb.api.models.series._
import com.thetvdb.api.models.update.{Update, UpdateResponse}
import com.thetvdb.api.models.user._
import spray.json.DefaultJsonProtocol


object Protocols extends DefaultJsonProtocol {
  implicit val postFormat = jsonFormat3(TokenRequest)
  implicit val tokenFormat = jsonFormat1(TokenResponse)

  implicit val seriesFormat = jsonFormat21(Series)
  implicit val seriesSearch = jsonFormat1(SeriesSearchResponse)
  implicit val seriesResponseFormat = jsonFormat1(SeriesResponse)

  implicit val episodeFormat = jsonFormat22(Episode)
  implicit val episodeRecordDataFormat = jsonFormat1(EpisodeResponse)

  implicit val actorFormat = jsonFormat8(Actor)
  implicit val actorResponseFormat = jsonFormat1(ActorResponse)

  implicit val languageFormat = jsonFormat4(Language)
  implicit val languagesRecordData = jsonFormat1(LanguagesResponse)
  implicit val languageRecordData = jsonFormat1(LanguageResponse)

  implicit val updateFormat = jsonFormat2(Update)
  implicit val updateResponseFormat = jsonFormat1(UpdateResponse)

  implicit val episodeInSeriesFormat = jsonFormat1(SeriesEpisodesResponse)

  implicit val userFormat = jsonFormat3(User)
  implicit val userResponseFormat = jsonFormat1(UserResponse)

  implicit val userFavoritesFormat = jsonFormat1(UserFavorites)
  implicit val serFavoritesResponseFormat = jsonFormat1(UserFavoritesResponse)

  implicit val userRatingsFormat = jsonFormat3(UserRatings)
  implicit val userRatingsResponseFormat = jsonFormat1(UserRatingsResponse)
}
