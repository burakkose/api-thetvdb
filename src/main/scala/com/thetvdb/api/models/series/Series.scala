package com.thetvdb.api.models.series

case class Series(
                   id: Option[Int],
                   seriesName: Option[String],
                   aliases: Option[Array[String]],
                   banner: Option[String],
                   seriesId: Option[String],
                   status: Option[String],
                   firstAired: Option[String],
                   network: Option[String],
                   networkId: Option[String],
                   runtime: Option[String],
                   genre: Option[Array[String]],
                   overview: Option[String],
                   lastUpdated: Option[Int],
                   airsDayOfWeek: Option[String],
                   airsTime: Option[String],
                   rating: Option[String],
                   imdbId: Option[String],
                   zap2itId: Option[String],
                   added: Option[String],
                   siteRating: Option[Double],
                   siteRatingCount: Option[Int]
                 )

case class SeriesResponse(data: Series)

case class SeriesSearchResponse(data: List[Series])
