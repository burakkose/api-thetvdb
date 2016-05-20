package com.thetvdb.api.models.episode

case class Episode(
                    id: Int,
                    airedSeason: Int,
                    airedEpisodeNumber: Int,
                    episodeName: String,
                    firstAired: String,
                    guestStars: Array[String],
                    director: String,
                    writers: Array[String],
                    overview: String,
                    lastUpdated: Int,
                    dvdDiscid: String,
                    dvdSeason: Int,
                    dvdEpisodeNumber: Double,
                    dvdChapter: Double,
                    absoluteNumber: Int,
                    filename: String,
                    seriesId: Int,
                    thumbWidth: String,
                    thumbHeight: String,
                    imdbId: String,
                    siteRating: Double,
                    siteRatingCount: Int
                  )

case class EpisodeResponse(data: Episode)
