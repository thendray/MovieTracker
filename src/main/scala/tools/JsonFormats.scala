package tools

import actors.MovieTrackerRegistry.ActionPerformed
import models.{Film, Films}
import spray.json.DefaultJsonProtocol
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat3}

object JsonFormats {

  import DefaultJsonProtocol._

  implicit val filmJsonFormat = jsonFormat6(Film)
  implicit val filmsJsonFormat = jsonFormat1(Films)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)

}
