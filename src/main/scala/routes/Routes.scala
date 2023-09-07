package routes

import actors.MovieTrackerRegistry
import actors.MovieTrackerRegistry._
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import configs.AppConfiguration
import models.requests.FilmCard
import models.responses.{ActionResult, ConfirmResponse, FilmCards}
import models.{Film, Films}

import scala.concurrent.Future

class Routes(movieTrackerRegistry: ActorRef[MovieTrackerRegistry.Command])
            (implicit val system: ActorSystem[_]) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import tools.JsonFormats._

  private implicit val timeout =
    Timeout.
      create(AppConfiguration.configs.
        getDuration("my-app.routes.ask-timeout")
    )

  private def getFilms(): Future[Option[Films]] =
    movieTrackerRegistry.ask(GetFilms)

  private def addFilm(film: FilmCard): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(AddFilm(film, _))

  private def updateFilm(film: Film): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(UpdateFilm(film, _))

  private def deleteFilm(filmId: Int): Future[ConfirmResponse] =
    movieTrackerRegistry.ask(DeleteFilm(filmId, _))


  private def findFilmsByKeyWord(keyWord: String, limit: Int): Future[Option[FilmCards]] =
    movieTrackerRegistry.ask(FindFilmsByKeyWord(keyWord, limit, _))



  val movieTrackerRoutes: Route =
    pathPrefix("films") {
      concat(

        path("find_by") {
          concat(
            get {
              parameters(Symbol("key_word").as[String], Symbol("limit").as[Int]) {
                (keyWord: String, limit: Int) => {
                  onSuccess(findFilmsByKeyWord(keyWord, limit)) {
                    case Some(films) => complete(films)
                    case None => complete(StatusCodes.NotFound)
                  }
                }
              }
            }
          )
        },

        path("all") {
          get {
            onSuccess(getFilms()) {
              case Some(films) => complete(films)
              case None => complete(StatusCodes.NotFound)
            }
          }
        },
        pathPrefix("film") {
          concat(
            path(IntNumber) {
              filmId =>
                delete {
                  val result: Future[ConfirmResponse] = deleteFilm(filmId)
                  onSuccess(result) { response =>
                    response.message match {
                      case Some(mess) => complete((StatusCodes.OK, mess))
                      case None => complete(StatusCodes.NotFound, "No film with such id")
                    }
                  }
                }
            },
            post {
              entity(as[FilmCard]) { filmCard =>
                val result: Future[ConfirmResponse] = addFilm(filmCard)
                onSuccess(result) { response =>
                  complete((StatusCodes.OK, response))
                }
              }
            },
            put {
              entity(as[Film]) { film =>
                val result: Future[ConfirmResponse] = updateFilm(film)
                onSuccess(result) { response =>
                  response.message match {
                    case Some(mess) => complete((StatusCodes.OK, mess))
                    case None => complete(StatusCodes.NotFound, "No film with such id")
                  }
                }
              }
            }
          )
        }
      )
    }
}
