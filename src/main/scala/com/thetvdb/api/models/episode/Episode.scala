package com.thetvdb.api.models.episode

case class Episode(
                    id: Option[Int],
                    airedSeason: Option[Int],
                    airedEpisodeNumber: Option[Int],
                    episodeName: Option[String],
                    firstAired: Option[String],
                    guestStars: Option[Array[String]],
                    director: Option[String],
                    writers: Option[Array[String]],
                    overview: Option[String],
                    lastUpdated: Option[Int],
                    dvdDiscid: Option[String],
                    dvdSeason: Option[Int],
                    dvdEpisodeNumber: Option[Double],
                    dvdChapter: Option[Double],
                    absoluteNumber: Option[Int],
                    filename: Option[String],
                    seriesId: Option[Int],
                    thumbWidth: Option[String],
                    thumbHeight: Option[String],
                    imdbId: Option[String],
                    siteRating: Option[Double],
                    siteRatingCount: Option[Int]
                  )

case class EpisodeResponse(data: Episode)

case class SeriesEpisodesResponse(data: List[Episode])