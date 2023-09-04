package models

case class Film(
                 id: Int,
                 name: String,
                 year: Int,
                 genre: String,
                 personalRating: Double,
                 userNote: String
               )

case class Films(films: Seq[Film])