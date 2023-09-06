package models

case class Film(
                 id: Option[Int],
                 name: String,
                 year: Int,
                 genre: String,
                 personalRating: Option[Double],
                 userNote:Option[String]
               )

case class Films(films: Seq[Film])