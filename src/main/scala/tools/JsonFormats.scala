package tools

import models._
import models.requests.FilmCard
import models.responses.{ConfirmResponse, FilmCards}
import spray.json.DefaultJsonProtocol
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat3}

object JsonFormats {

  import DefaultJsonProtocol._

  implicit val filmJsonFormat = jsonFormat6(Film)
  implicit val filmsJsonFormat = jsonFormat1(Films)
  implicit val filmCardJsonFormat = jsonFormat3(FilmCard)
  implicit val filmCardsJsonFormat = jsonFormat1(FilmCards)

  implicit val confirmResponseJsonFormat = jsonFormat1(ConfirmResponse)

}
