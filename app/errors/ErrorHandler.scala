package errors

import javax.inject._
import play.api.mvc.{RequestHeader, Result}
import scala.concurrent.Future
import play.api.mvc._
import play.api.http.Status._
import play.api.mvc.Results._
import play.api.http.DefaultHttpErrorHandler

@Singleton
class ErrorHandler extends DefaultHttpErrorHandler {
    override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
        exception match {
            case e: models.Error => Future.successful(InternalServerError(views.html.errorPage(e)))
            case _ => Future.successful(InternalServerError("Unknown error occurred"))
        }
    }
    override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
        statusCode match {
            case NOT_FOUND => Future.successful(NotFound(views.html.errorPage(models.Error(List(s"page not found: ${request.uri}")))))
            case BAD_REQUEST => Future.successful(NotFound(views.html.errorPage(models.Error(List("bad request")))))
        }
    }
}