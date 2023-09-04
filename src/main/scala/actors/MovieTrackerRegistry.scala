package actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import models.Films
import services.{FilmService, FilmServiceTrait}

object MovieTrackerRegistry {
  sealed trait Command

  final case class GetFilms(replyTo: ActorRef[Films]) extends Command

  private val filmService: FilmServiceTrait = new FilmService

  final case class ActionPerformed(description: String)


  def apply(): Behavior[Command] = registry

  private def registry: Behavior[Command] = {
    Behaviors.receiveMessage {
      case GetFilms(replyTo) =>
        replyTo ! filmService.getFilms()
        Behaviors.same
    }
  }

}
