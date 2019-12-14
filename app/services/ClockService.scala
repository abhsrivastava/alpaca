package services

import javax.inject._
import play.api.libs.ws.WSClient
import play.api.libs.ws.ahc.AhcCurlRequestLogger
import config.AlpacaConfig
import scala.concurrent.duration._
import models.{ClockEntity, ClockEntityInternal, Error}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

@Singleton
class ClockService @Inject()(c: AlpacaConfig, ws: WSClient) extends BaseService(c) {
    val endpoint = s"${c.endpoint}/v2/clock"
    def getClockEntity(): Future[Either[Error, ClockEntity]] = {
    ws
        .url(endpoint)
        .addHttpHeaders(getHeaders():_*)
        .withRequestTimeout(10 seconds)
        .withFollowRedirects(true)
        .withRequestFilter(AhcCurlRequestLogger())
        .get
        .map{response => 
            import ClockEntityInternal._
            response.json.validate[ClockEntityInternal]
        }.map{
            case JsSuccess(cei,_) => Right(ClockEntity(cei, c))
            case e : JsError => Left(Error(List(JsError.toJson(e).toString)))
        }
    }

}