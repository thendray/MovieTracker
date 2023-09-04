package services

import models.{Film, Films}
import repositories.FilmRepositoryTrait
import repositories.implementations.FilmRepositorySlickEdition


trait FilmServiceTrait {
  protected val filmRepository: FilmRepositoryTrait = new FilmRepositorySlickEdition

  def getFilms(): Films

}

class FilmService extends FilmServiceTrait {

  override def getFilms(): Films = {
    filmRepository.getAllFilms()
  }
}

object FilmService {
  def apply(): FilmServiceTrait = {
    new FilmService
  }
}
