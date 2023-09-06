package repositories

import models.{Film, Films}
import models.requests.FilmCard

import scala.concurrent.Future

trait FilmRepositoryTrait {
  def update(oldFilm: Option[Film], newFilm: Film): Unit

  def delete(film: Option[Film]): Unit

  def getFilmById(filmId: Int): Future[Option[Film]]

  def add(film: Film): Future[Int]

  def getFilmByNameYearGenre(filmCard: FilmCard): Future[Option[Film]]


  def getAllFilms(): Future[Option[Films]]

}
