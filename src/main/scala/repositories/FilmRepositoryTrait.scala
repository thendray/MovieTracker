package repositories

import models.{Film, Films}
import models.requests.FilmCard

import scala.concurrent.Future

trait FilmRepositoryTrait {
  def update(oldFilmId: Int, newFilm: Film): Unit

  def delete(film: Film): Future[Int]

  def getFilmById(filmId: Int): Future[Option[Film]]

  def add(film: Film): Future[Int]

  def getFilmByNameYearGenre(filmCard: FilmCard): Future[Option[Film]]


  def getAllFilms(): Future[Option[Films]]

}
