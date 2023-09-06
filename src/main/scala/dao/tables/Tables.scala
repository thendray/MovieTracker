package dao.tables

import slick.jdbc.MySQLProfile.api._
import models.Film
import slick.lifted.ProvenShape

object Tables {

  class FilmTable(tag: Tag) extends Table[Film](tag, "Films") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def year = column[Int]("year")
    def genre = column[String]("genre")
    def rating = column[Option[Double]]("personal_rating", O.Default(None))
    def note = column[Option[String]]("user_note", O.Default(None))

    override def * : ProvenShape[Film] = (id.?, name, year, genre, rating, note) <> (Film.tupled, Film.unapply)
  }

  val films = TableQuery[FilmTable]

}
