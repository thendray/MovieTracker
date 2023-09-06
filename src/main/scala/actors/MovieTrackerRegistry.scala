package actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import models.requests.FilmCard
import models.{Film, Films}
import models.responses.ConfirmResponse
import services.{FilmService, FilmServiceTrait}

object MovieTrackerRegistry {
  sealed trait Command

  final case class GetFilms(replyTo: ActorRef[Option[Films]]) extends Command
  final case class AddFilm(filmCard: FilmCard, replyTo: ActorRef[ConfirmResponse]) extends Command
  final case class UpdateFilm(film: Film, replyTo: ActorRef[ConfirmResponse]) extends Command
  final case class DeleteFilm(filmId: Int, replyTo: ActorRef[ConfirmResponse]) extends Command

  private val filmService: FilmServiceTrait = new FilmService




  def apply(): Behavior[Command] = registry

  private def registry: Behavior[Command] = {
    Behaviors.receiveMessage {
      case GetFilms(replyTo) =>
        replyTo ! filmService.getFilms()
        Behaviors.same

      case AddFilm(filmCard, replyTo) =>
        replyTo ! filmService.addFilm(filmCard)
        Behaviors.same

      case UpdateFilm(film, replyTo) =>
        replyTo ! filmService.updateFilmInfo(film)
        Behaviors.same

      case DeleteFilm(filmId, replyTo) =>
        replyTo ! filmService.deleteFilm(filmId)
        Behaviors.same
    }
  }

}
