package controllers

import javax.inject._
import play.api.mvc._
import play.api._
import services.ClockService
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ClockController @Inject()(
    cs: ClockService,
    cc: ControllerComponents
    ) extends AbstractController(cc) {

    def index() = Action.async {
        cs.getClockEntity().map {
            case Left(e) => InternalServerError(e.errorMessages.mkString(","))
            case Right(ce) => Ok(views.html.clock(ce))
        }
    }
}