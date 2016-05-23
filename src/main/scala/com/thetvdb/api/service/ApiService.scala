package com.thetvdb.api.service

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.{/, Query}
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import akka.http.scaladsl.model.{Uri, _}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.ActorMaterializer
import com.thetvdb.api.models.actor.{Actor, ActorResponse}
import com.thetvdb.api.models.auth.{TokenRequest, TokenResponse}
import com.thetvdb.api.models.episode.{Episode, EpisodeResponse, SeriesEpisodesResponse}
import com.thetvdb.api.models.language.{Language, LanguageResponse, LanguagesResponse}
import com.thetvdb.api.models.series._
import com.thetvdb.api.models.update.{Update, UpdateResponse}
import com.thetvdb.api.models.user._
import com.thetvdb.api.operations.ApiOperations
import com.thetvdb.api.protocols.Protocols._
import com.thetvdb.api.utils.AuthException

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

trait BaseService {
  implicit protected val system: ActorSystem = ActorSystem()
  implicit protected val materializer: ActorMaterializer = ActorMaterializer()
  implicit protected val http: HttpExt = Http()
}

abstract class ApiService(apiKey: String, username: String, userKey: String)
  extends ApiOperations with BaseService {

  private var header: List[HttpHeader] = List.empty[HttpHeader]

  override def getActors(seriesID: String): Future[Option[List[Actor]]] = {
    http.singleRequest(HttpRequest(uri = SERIES_URL + / + s"${seriesID}/actors",
      headers = header)
    )
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[ActorResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getLanguage(langID: String): Future[Option[Language]] = {
    http.singleRequest(HttpRequest(uri = LANGUAGE_URL + / + langID, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[LanguageResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getLanguages(): Future[Option[List[Language]]] = {
    http.singleRequest(HttpRequest(uri = LANGUAGE_URL, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[LanguagesResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def refreshToken(): Future[Option[TokenResponse]] = {
    http.singleRequest(HttpRequest(uri = REFRESH_TOKEN_URL, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[Option[TokenResponse]]
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def buildConnection(): this.type = {
    val ref = Marshal(TokenRequest(apiKey, username, userKey)).to[RequestEntity]
      .flatMap { request => http.singleRequest(
        HttpRequest(
          uri = LOGIN_URL,
          method = HttpMethods.POST,
          entity = request)
      )
      }.flatMap {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        Unmarshal(entity).to[TokenResponse]
      case HttpResponse(code, _, _, _) =>
        Future.failed(new AuthException)
    }
    val accessToken = Await.result(ref, 5 seconds)
    header = List(headers.Authorization(OAuth2BearerToken(accessToken.token)))
    this
  }

  override def getSeries(id: String): Future[Option[Series]] = {
    http.singleRequest(HttpRequest(uri = SERIES_URL + / + id, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[SeriesResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def searchSeries(searchKey: String): Future[Option[List[Series]]] = {
    val queryParams = Query(Map("name" -> searchKey))
    http.singleRequest(HttpRequest(uri =
      Uri(SEARCH_SERIES_URL).withQuery(queryParams), headers = header)
    ).flatMap {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        Unmarshal(entity).to[SeriesSearchResponse].map(resp => Some(resp.data))
      case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
        Future.failed(new AuthException)
      case HttpResponse(code, _, _, _) =>
        Future.successful(None)
    }
  }

  override def getEpisode(episodeID: String): Future[Option[Episode]] = {
    http.singleRequest(HttpRequest(uri = EPISODE_URL + / + episodeID, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[EpisodeResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getUpdates(fromTime: String,
                          toTime: Option[String] = None): Future[Option[List[Update]]] = {
    val queryParam = Query(Map("fromTime" -> fromTime) ++ {
      toTime match {
        case Some(time) => Map("toTime" -> time)
        case None => Map.empty[String, String]
      }
    })
    http.singleRequest(HttpRequest(uri = Uri(UPDATE_URL).withQuery(queryParam), headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[UpdateResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getEpisodesBySeriesID(seriesID: String): Future[Option[List[Episode]]] = {
    http.singleRequest(
      HttpRequest(uri = SERIES_URL + / + seriesID + / + "episodes", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[SeriesEpisodesResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getUser(): Future[Option[User]] = {
    http.singleRequest(HttpRequest(uri = USER_URL, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[UserResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getUserFavorites(): Future[Option[UserFavorites]] = {
    http.singleRequest(HttpRequest(uri = USER_FAVORITES_URL, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[UserFavoritesResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getUserRatings(): Future[Option[List[UserRatings]]] = {
    http.singleRequest(HttpRequest(uri = USER_RATINGS_URL, headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[UserRatingsResponse].map(resp => Some(resp.data))
        case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
          Future.failed(new AuthException)
        case HttpResponse(code, _, _, _) =>
          Future.successful(None)
      }
  }

  override def getUserBannerRatings(): Future[Option[List[UserRatings]]] = {
    getUserRatingsByFilter("banner")
  }

  override def getUserSeriesRatings(): Future[Option[List[UserRatings]]] = {
    getUserRatingsByFilter("series")
  }

  override def getUserEpisodeRatings(): Future[Option[List[UserRatings]]] = {
    getUserRatingsByFilter("episode")
  }

  private def getUserRatingsByFilter(filter: String): Future[Option[List[UserRatings]]] = {
    val queryParam = Query(Map("itemType" -> filter))
    http.singleRequest(HttpRequest(uri =
      Uri(USER_RATINGS_URL + "/query").withQuery(queryParam), headers = header)
    ).flatMap {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        Unmarshal(entity).to[UserRatingsResponse].map(resp => Some(resp.data))
      case HttpResponse(StatusCodes.Unauthorized, _, _, _) =>
        Future.failed(new AuthException)
      case HttpResponse(code, _, _, _) =>
        Future.successful(None)
    }
  }
}
