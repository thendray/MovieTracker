package actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import models.Films
import models.responses.ConfirmResponse
import services.{FilmService, FilmServiceTrait}

object MovieTrackerRegistry {
  sealed trait Command

  final case class GetFilms(replyTo: ActorRef[Films]) extends Command
  final case class AddFilm(replyTo: ActorRef[ConfirmResponse]) extends Command

  private val filmService: FilmServiceTrait = new FilmService




  def apply(): Behavior[Command] = registry

  private def registry: Behavior[Command] = {
    Behaviors.receiveMessage {
      case GetFilms(replyTo) =>
        replyTo ! filmService.getFilms()
        Behaviors.same

      case
    }
  }

}
