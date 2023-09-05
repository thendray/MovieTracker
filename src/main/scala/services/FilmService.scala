package services

import models.requests.FilmCard
import models.responses.ConfirmResponse
import models.{Film, Films}
import repositories.FilmRepositoryTrait
import repositories.implementations.FilmRepositorySlickEdition


trait FilmServiceTrait {
  def addFilm(filmCard: FilmCard): ConfirmResponse

  protected val filmRepository: FilmRepositoryTrait = new FilmRepositorySlickEdition

  def getFilms(): Option[Films]

}

class FilmService extends FilmServiceTrait {

  override def getFilms(): Option[Films] = {
    filmRepository.getAllFilms()
  }

  override def addFilm(filmCard: FilmCard): ConfirmResponse = {
    ConfirmResponse("OK add")
  }
}

object FilmService {
  def apply(): FilmServiceTrait = {
    new FilmService
  }
}
