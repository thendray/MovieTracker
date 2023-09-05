package tools

import models._
import models.requests.FilmCard
import models.responses.ConfirmResponse
import spray.json.DefaultJsonProtocol
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat3}

object JsonFormats {

  import DefaultJsonProtocol._

  implicit val filmJsonFormat = jsonFormat6(Film)
  implicit val filmsJsonFormat = jsonFormat1(Films)
  implicit val filmardJsonFormat = jsonFormat3(FilmCard)

  implicit val confirmResponseJsonFormat = jsonFormat1(ConfirmResponse)

}
