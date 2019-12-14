package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import javax.inject._
import models.AccountEntity
import services.AccountServices
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AccountController @Inject()(
    accountServices: AccountServices,
    cc: ControllerComponents) extends AbstractController(cc) {
    def index() = Action.async {
        accountServices.getAccountInfo().map {
            case Right(ae) => Ok(views.html.account(ae))
            case Left(errMsg) => InternalServerError(errMsg.errorMessages.mkString(","))
        }
    }
}