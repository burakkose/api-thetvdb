package com.thetvdb.api.models.series

case class Series(
                   id: Int,
                   seriesName: String,
                   aliases: Array[String],
                   banner: String,
                   seriesId: String,
                   status: String,
                   firstAired: String,
                   network: String,
                   networkId: String,
                   runtime: String,
                   genre: Array[String],
                   overview: String,
                   lastUpdated: Int,
                   airsDayOfWeek: String,
                   airsTime: String,
                   rating: String,
                   imdbId: String,
                   zap2itId: String,
                   added: String,
                   siteRating: Double,
                   siteRatingCount: Int
                 )

case class SeriesSearchData(
                             aliases: Array[String],
                             banner: String,
                             firstAired: String,
                             id: Int,
                             network: String,
                             overview: String,
                             seriesName: String,
                             status: String
                           )

case class SeriesResponse(data: Series)

case class SeriesSearchResponse(data: List[SeriesSearchData])

