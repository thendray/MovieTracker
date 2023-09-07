package models.forsttp

case class BigFilmModel(`type`: String,
                        title: String,
                        year: Option[Int],
                        streamingInfo: Map[String, Seq[Country]],
                        imdbId: String,
                        tmdbId: Long,
                        originalTitle: String,
                        genres: Seq[Genre],
                        directors: Option[Seq[String]]
                       )


case class Genre(id: Int, name: String)

case class Country(service: String,
                   streamingType: String,
                   link: String,
                   audios: Seq[Audios],
                   subtitles: Seq[Subtitle],
                   availableSince: Long)

case class Audios(language: String, region: String)

case class Subtitle(closedCaptions: Boolean, locale: Audios)

case class FilmsModel(result: Seq[BigFilmModel], hasMore: Boolean, nextCursor: String)