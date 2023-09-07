package tools

import models.forsttp._
import spray.json.DefaultJsonProtocol

object JsonFormatForFilmResponse {
  import DefaultJsonProtocol._

  implicit val jsonGereFormat = jsonFormat2(Genre)
  implicit val jsonAuditorsFormat = jsonFormat2(Audios)
  implicit val jsonSubtitleFormat = jsonFormat2(Subtitle)
  implicit val jsonCountryFormat = jsonFormat6(Country)
  implicit val jsonFilModelFormat = jsonFormat9(BigFilmModel)
  implicit val jsonFilmsFormat = jsonFormat3(FilmsModel)

}
