package services

import models.requests.FilmCard
import models.responses.ConfirmResponse
import models.{Film, Films}
import repositories.FilmRepositoryTrait
import repositories.implementations.FilmRepositorySlickEdition


trait FilmServiceTrait {
  def deleteFilm(filmId: Int): ConfirmResponse

  def updateFilmInfo(film: Film): ConfirmResponse

  def addFilm(filmCard: FilmCard): ConfirmResponse

  protected val filmRepository: FilmRepositoryTrait = new FilmRepositorySlickEdition

  def getFilms(): Option[Films]

}

class FilmService extends FilmServiceTrait {

  override def getFilms(): Option[Films] = {
    filmRepository.getAllFilms()
  }

  override def addFilm(filmCard: FilmCard): ConfirmResponse = {

    val hasYet = filmRepository.getFilmByNameYearGenre(filmCard)

    hasYet match {
      case Some(_) => {
        val newFilm: Option[Film] = filmRepository.add(
          Film(
            id = None,
            name = filmCard.name,
            year = filmCard.year,
            genre = filmCard.genre,
            personalRating = None,
            userNote = None
          )
        )
        ConfirmResponse(Option(f"Film was successfully added with id=${newFilm.get.id}"))
      }
      case None => ConfirmResponse(Option("Film with such name, year and genre has already exists on your tracker!"))
    }

  }

  override def deleteFilm(filmId: Int): ConfirmResponse = {
    val film: Option[Film] = filmRepository.getFilmById(filmId)

    film match {
      case Some(value) => {
        filmRepository.delete(film)
        ConfirmResponse(Option("Film was successfully deleted!"))
      }
      case None => ConfirmResponse(None)
    }

  }

  override def updateFilmInfo(newFilm: Film): ConfirmResponse = {
    val oldFilm: Option[Film] = filmRepository.getFilmById(newFilm.id.get)

    oldFilm match {
      case Some(value) => {
        filmRepository.update(oldFilm, newFilm)
        ConfirmResponse(Option("Film info was successfully updated!"))
      }
      case None => ConfirmResponse(None)
    }
  }
}

object FilmService {
  def apply(): FilmServiceTrait = {
    new FilmService
  }
}
