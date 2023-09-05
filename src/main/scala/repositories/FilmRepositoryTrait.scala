package repositories

import models.Films

trait FilmRepositoryTrait {

  def getAllFilms(): Option[Films]

}
