package com.thetvdb.api.service

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.ActorMaterializer
import com.thetvdb.api.models.actor.{Actor, ActorResponse}
import com.thetvdb.api.models.auth.{TokenRequest, TokenResponse}
import com.thetvdb.api.models.episode.{Episode, EpisodeResponse}
import com.thetvdb.api.models.language.{Language, LanguageResponse, LanguagesResponse}
import com.thetvdb.api.models.series.{Series, SeriesResponse, SeriesSearchData, SeriesSearchResponse}
import com.thetvdb.api.operations.ApiOperations
import com.thetvdb.api.protocols.Protocols._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

trait BaseService {
  implicit protected val system: ActorSystem = ActorSystem()
  implicit protected val materializer: ActorMaterializer = ActorMaterializer()
  implicit protected val http: HttpExt = Http()
}

abstract class ClientService(apiKey: String) extends ApiOperations with BaseService {

  private var header: List[HttpHeader] = List.empty[HttpHeader]

  override def getActors(seriesID: String): Future[List[Actor]] = {
    http.singleRequest(HttpRequest(uri =
      s"https://api.thetvdb.com/series/${seriesID}/actors", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[ActorResponse].map(_.data)
        case HttpResponse(code, _, _, _) =>
          Future.successful(List.empty[Actor])
      }
  }

  override def getLanguage(langID: String): Future[Language] = {
    http.singleRequest(HttpRequest(uri =
      s"https://api.thetvdb.com/languages/${langID}", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[LanguageResponse].map(_.data)
        case HttpResponse(code, _, _, _) =>
          Future.successful(null)
      }
  }

  override def getLanguages(): Future[List[Language]] = {
    http.singleRequest(HttpRequest(uri =
      "https://api.thetvdb.com/languages", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[LanguagesResponse].map(_.data)
        case HttpResponse(code, _, _, _) =>
          Future.successful(List.empty[Language])
      }
  }

  override def refreshToken(): Future[TokenResponse] = {
    http.singleRequest(HttpRequest(uri =
      "https://api.thetvdb.com/refresh_token", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[TokenResponse]
        case HttpResponse(code, _, _, _) =>
          Future.successful(null)
      }
  }

  override def buildConnection(): this.type = {
    val ref = Marshal(TokenRequest(apiKey)).to[RequestEntity].flatMap { request =>
      http.singleRequest(HttpRequest(uri = "https://api.thetvdb.com/login",
        method = HttpMethods.POST,
        entity = request
      ))
    }.flatMap(s => Unmarshal(s.entity).to[TokenResponse])
    val accessToken = Await.result(ref, 5 seconds)
    header = List(headers.Authorization(OAuth2BearerToken(accessToken.token)))
    this
  }

  override def getSeries(id: String): Future[Series] = {
    http.singleRequest(HttpRequest(uri =
      s"https://api.thetvdb.com/series/${id}", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[SeriesResponse].map(_.data)
        case HttpResponse(code, _, _, _) =>
          Future.successful(null)
      }
  }

  override def searchSeries(searchKey: String): Future[List[SeriesSearchData]] = {
    val queryParams = Map("name" -> searchKey)
    http.singleRequest(HttpRequest(uri =
      Uri("https://api.thetvdb.com/search/series").withQuery(Query(queryParams)),
      headers = header
    )).flatMap {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        Unmarshal(entity).to[SeriesSearchResponse].map(_.data)
      case HttpResponse(code, _, _, _) =>
        Future.successful(List.empty[SeriesSearchData])
    }
  }

  override def getEpisode(episodeID: String): Future[Episode] = {
    http.singleRequest(HttpRequest(uri =
      s"https://api.thetvdb.com/episodes/${episodeID}", headers = header))
      .flatMap {
        case HttpResponse(StatusCodes.OK, headers, entity, _) =>
          Unmarshal(entity).to[EpisodeResponse].map(_.data)
        case HttpResponse(code, _, _, _) =>
          Future.successful(null)
      }
  }
}
