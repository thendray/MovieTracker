package repositories.implementations

import models.{Film, Films}
import repositories.FilmRepositoryTrait

class FilmRepositorySlickEdition extends FilmRepositoryTrait {
  override def getAllFilms(): Films = {
    Films(Seq(Film(1, "name", 1900, "drama", 5.5, "some note")))
  }
}

object FilmRepositorySlickEdition {
  def apply(): FilmRepositoryTrait = {
    new FilmRepositorySlickEdition
  }
}
