package com.thetvdb.api.models.actor

case class Actor(
                  id: Int,
                  name: String,
                  role: String,
                  sortOrder: Int,
                  image: String,
                  imageAuthor: Int,
                  imageAdded: String,
                  lastUpdated: String
                )

case class ActorResponse(data: List[Actor])
