package services

import models.requests.FilmCard
import models.responses.ConfirmResponse
import models.{Film, Films}
import repositories.FilmRepositoryTrait
import repositories.implementations.FilmRepositorySlickEdition

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


trait FilmServiceTrait {
  def deleteFilm(filmId: Int): ConfirmResponse

  def updateFilmInfo(film: Film): ConfirmResponse

  def addFilm(filmCard: FilmCard): ConfirmResponse

  protected val filmRepository: FilmRepositoryTrait = new FilmRepositorySlickEdition

  def getFilms(): Option[Films]

}

class FilmService extends FilmServiceTrait {

  import configs.MyExecutionContext._

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
        filmRepository.delete(film)
        ConfirmResponse(Option("Film was successfully deleted!"))
      }
      case None => ConfirmResponse(None)
    }

  }

  override def updateFilmInfo(newFilm: Film): ConfirmResponse = {
    val oldFilm: Option[Film] = Await.result(filmRepository.getFilmById(newFilm.id.get), inf)

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
