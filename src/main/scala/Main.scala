import actors.MovieTrackerRegistry
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import routes.Routes

import scala.util.{Failure, Success}

object Main {

  private def start(routes: Route)
                   (implicit system: ActorSystem[_]): Unit = {

    import system.executionContext

    val futureBinding = Http()
      .newServerAt(interface = "localhost", port = 8000)
      .bind(routes)

    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }

  }

  def main(args: Array[String]): Unit = {

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      val movieTrackerRegistry = context.spawn(MovieTrackerRegistry(), "MovieTrackerActor")
      context.watch(movieTrackerRegistry)

      val routes = new Routes(movieTrackerRegistry)(context.system)


      start(routes.movieTrackerRoutes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "MovieTracker")
    //#server-bootstrapping
  }


}