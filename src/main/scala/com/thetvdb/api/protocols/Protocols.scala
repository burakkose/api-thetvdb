package com.thetvdb.api.protocols

import com.thetvdb.api.models.{Actor, Authentication}
import spray.json.DefaultJsonProtocol

trait Protocols extends DefaultJsonProtocol {

  implicit protected val authFormat = jsonFormat3(Authentication.apply)
  implicit protected val actorFormat = jsonFormat9(Actor.apply)
}
