import actors.MovieTrackerRegistry
import actors.MovieTrackerRegistry._
import models.Films

import akka.actor.typed.{ActorRef, ActorSystem}
import com.typesafe.config.{Config, ConfigFactory}
import akka.actor.typed.scaladsl.AskPattern._


import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout

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
