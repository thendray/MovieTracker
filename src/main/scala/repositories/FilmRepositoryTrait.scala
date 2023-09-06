package repositories

import models.{Film, Films}
import models.requests.FilmCard

trait FilmRepositoryTrait {
  def update(oldFilm: Option[Film], newFilm: Film): Unit

  def delete(film: Option[Film]): Unit

  def getFilmById(filmId: Int): Option[Film]

  def add(film: Film): Option[Film]

  def getFilmByNameYearGenre(filmCard: FilmCard): Option[Film]


  def getAllFilms(): Option[Films]

}
