package services

import models.requests.FilmCard
import models.responses.{ConfirmResponse, FilmCards}
import models.{Film, Films}
import repositories.FilmRepositoryTrait
import repositories.implementations.FilmRepositorySlickEdition

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration


trait FilmServiceTrait {
  def findFilmsByKeyWord(keyWord: String, limit: Int): Option[FilmCards]


  def deleteFilm(filmId: Int): ConfirmResponse

  def updateFilmInfo(film: Film): ConfirmResponse

  def addFilm(filmCard: FilmCard): ConfirmResponse

  protected val filmRepository: FilmRepositoryTrait = new FilmRepositorySlickEdition

  def getFilms(): Option[Films]

}

class FilmService extends FilmServiceTrait {


  override def getFilms(): Option[Films] = {
    val films = filmRepository.getAllFilms()

    Await.result(films, inf)
  }

  override def addFilm(filmCard: FilmCard): ConfirmResponse = {

    val hasYet = Await.result(filmRepository.getFilmByNameYearGenre(filmCard), inf)

    hasYet match {
      case None => {
        val newFilmIdFuture: Future[Int] = filmRepository.add(
            Film(
              id = None,
              name = filmCard.name,
              year = filmCard.year,
              genre = filmCard.genre,
              personalRating = None,
              userNote = None
            ))
        val newFilmId = Await.result(newFilmIdFuture, inf)
        ConfirmResponse(Option(f"Film was successfully added with id=${newFilmId}"))
      }
      case Some(_) => ConfirmResponse(Option("Film with such name, year and genre has already exists on your tracker!"))
    }

  }

  private val inf: Duration.Infinite = Duration.Inf

  override def deleteFilm(filmId: Int): ConfirmResponse = {
    val film: Option[Film] = Await.result(filmRepository.getFilmById(filmId), inf)

    film match {
      case Some(value) => {
        val id = Await.result(filmRepository.delete(value), inf)
        ConfirmResponse(Option(f"Film was successfully deleted!"))
      }
      case None => ConfirmResponse(None)
    }

  }

  override def updateFilmInfo(newFilm: Film): ConfirmResponse = {
    val oldFilm: Option[Film] = Await.result(filmRepository.getFilmById(newFilm.id.get), inf)

    oldFilm match {
      case Some(film) => {
        filmRepository.update(film.id.get, newFilm)
        ConfirmResponse(Option("Film info was successfully updated!"))
      }
      case None => ConfirmResponse(None)
    }
  }

  override def findFilmsByKeyWord(keyWord: String, limit: Int): Option[FilmCards] = {
    val filmCards = SttpRequestService.findFilmsByKeyWord(keyWord, limit)
    filmCards
  }

}

object FilmService {
  def apply(): FilmServiceTrait = {
    new FilmService
  }
}
