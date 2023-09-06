package repositories.implementations

import configs.DbConfiguration
import dao.tables.Tables
import models.requests.FilmCard
import models.{Film, Films}
import repositories.FilmRepositoryTrait
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class FilmRepositorySlickEdition extends FilmRepositoryTrait {

  private val db = DbConfiguration.db
  private val films = Tables.films

  private val schema = films.schema
  db.run(schema.createIfNotExists)

  import configs.MyExecutionContext._

  override def getAllFilms(): Future[Option[Films]] = {
    val result = db.run(films.result).map(x => Option(Films(x)))
    result
  }

  override def update(oldFilm: Option[Film], newFilm: Film): Unit = ???

  override def delete(film: Option[Film]): Unit = ???

  override def getFilmById(filmId: Int): Future[Option[Film]] = {
    val query = films.filter(_.id === filmId).result.headOption
    db.run(query)
  }

  override def add(film: Film): Future[Int] = {
    val returningFilmIdQuery = films += film

    db.run(returningFilmIdQuery)
  }

  override def getFilmByNameYearGenre(filmCard: FilmCard): Future[Option[Film]] = {
    val query = films.filter( x =>
      x.name === filmCard.name && x.genre === filmCard.genre && x.year === filmCard.year)
      .result
      .headOption

    db.run(query)
  }

}

object FilmRepositorySlickEdition {
  def apply(): FilmRepositoryTrait = {
    new FilmRepositorySlickEdition
  }
}
