package services

import configs.AppConfiguration
import models.Films
import models.forsttp.{FilmsModel, Genre}
import models.requests.FilmCard
import models.responses.FilmCards
import sttp.client4._
import sttp.client4.sprayJson.asJson



object SttpRequestService {

  import tools.JsonFormatForFilmResponse._

  private val backend = DefaultSyncBackend()


  private val myHeader = Map(
    "X-RapidAPI-Key" -> AppConfiguration.configs.getString("my-app.movie-api.key"),
    "X-RapidAPI-Host" -> AppConfiguration.configs.getString("my-app.movie-api.host")
  )


  def findFilmsByKeyWord(keyWord: String, limit: Int): Option[FilmCards] = {
    val parameters = Map(
      "services" -> "netflix",
      "country" -> "us",
      "output_language" -> "en",
      "keyword" -> keyWord
    )

    val connectionString = uri"https://streaming-availability.p.rapidapi.com/search/filters?$parameters"

    val request = basicRequest
      .headers(myHeader)
      .get(connectionString)
      .response(asJson[FilmsModel])

    val response = request.send(backend).body


    response match {
      case Right(value) => if (value.result.isEmpty) None else
        Option(FilmCards(value.result.sortWith(_.title.length > _.title.length)
          .map(model =>
            FilmCard(
              model.title,
              model.year.getOrElse(0),
              model.genres.headOption.getOrElse(Genre(0, "No gene")).name))
          .take(limit)))
      case Left(e) => None

    }



  }
}



