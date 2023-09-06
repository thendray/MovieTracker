package repositories.implementations

import models.requests.FilmCard
import models.{Film, Films}
import repositories.FilmRepositoryTrait

class FilmRepositorySlickEdition extends FilmRepositoryTrait {
  override def getAllFilms(): Option[Films] = {
    Option(Films(Seq(Film(Option(1), "name", 1900, "drama", None, None))))
  }

  override def update(oldFilm: Option[Film], newFilm: Film): Unit = ???

  override def delete(film: Option[Film]): Unit = ???

  override def getFilmById(filmId: Int): Option[Film] = ???

  override def add(film: Film): Option[Film] = ???

  override def getFilmByNameYearGenre(filmCard: FilmCard): Option[Film] = ???

}

object FilmRepositorySlickEdition {
  def apply(): FilmRepositoryTrait = {
    new FilmRepositorySlickEdition
  }
}
