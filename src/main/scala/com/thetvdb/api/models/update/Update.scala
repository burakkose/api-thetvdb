package com.thetvdb.api.models.update

case class Update(id: Int, lastUpdated: Int)

case class UpdateResponse(data: List[Update])