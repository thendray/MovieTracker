package routes

import actors.MovieTrackerRegistry
import actors.MovieTrackerRegistry._
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import models.requests.FilmCard
import models.responses.{ActionResult, ConfirmResponse}
import models.{Film, Films}

import scala.concurrent.Future

class Routes(movieTrackerRegistry: ActorRef[MovieTrackerRegistry.Command])
            (implicit val system: ActorSystem[_]) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import tools.JsonFormats._

  private val configs: Config = ConfigFactory.load("app.conf")
  private implicit val timeout = Timeout.
    create(configs.
      getDuration("my-app.routes.ask-timeout")
    )

  def getFilms(): Future[Films] =
    movieTrackerRegistry.ask(GetFilms)

  def addFilm(film: FilmCard): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(AddFilm)

  def updateFilm(film: Film): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(updateFilmInfo)

  def deleteFilm(film: Film): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(DeleteFilm)



  val movieTrackerRoutes: Route =
    pathPrefix("films") {
      concat(
        pathEnd {
          concat(
            get {
              complete(getFilms())
            }
          )
        }
      )
    }
}
