package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import javax.inject._
import play.api.cache._
import models.AccountEntity
import services.AccountServices
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import models.CacheKeys._

@Singleton
class AccountController @Inject()(
    cache: AsyncCacheApi,
    accountServices: AccountServices,
    cc: ControllerComponents) extends AbstractController(cc) {
    def index() = Action.async {
        val acInfo : Future[Either[models.Error, AccountEntity]] = (for {
            accountInfo <- cache.get[AccountEntity](profileKey)
        } yield {
            accountInfo.fold(getAccountInfo(cache)){ac => 
                Future.successful(Right(ac))
            }
        }).flatten

        acInfo.map {
            case Right(ae) => Ok(views.html.account(ae))
            case Left(errMsg) => InternalServerError(errMsg.errorMessages.mkString(","))
        }
    }

    def getAccountInfo(cache: AsyncCacheApi) : Future[Either[models.Error, AccountEntity]] = {
        (for {
            accEither <- accountServices.getAccountInfo()
        } yield {
            accEither.fold(
                e => Future.successful(Left(e)),
                ac => cache.set(profileKey, ac).map(_ => Right(ac))
            )
        }).flatten
    }
}