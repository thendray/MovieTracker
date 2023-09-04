package services

import models.{Film, Films}


trait FilmServiceTrait {

  def getFilms(): Films

}

class FilmService extends FilmServiceTrait {
  override def getFilms(): Films = {
    Films(Seq(Film(1, "name", 1900, "drama", 5.5, "b")))
  }
}

object FilmService {
  def apply(): FilmServiceTrait = {
    new FilmService
  }
}
