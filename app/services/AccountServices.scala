package services
import config.AlpacaConfig
import play.api.libs.ws.WSClient
import javax.inject._
import scala.concurrent.duration._
import models._
import play.api.libs.ws.ahc.AhcCurlRequestLogger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import play.api.libs.json._

@Singleton
class AccountServices @Inject()(c: AlpacaConfig, ws: WSClient) {
    val endpoint = s"${c.endpoint}/v2/account"
    import AccountEntityInternal._
    def getAccountInfo() : Future[Either[Error, AccountEntity]] = {
        ws
            .url(endpoint)
            .addHttpHeaders(
                "APCA-API-KEY-ID" -> c.keyId, 
                "APCA-API-SECRET-KEY" -> c.secret,
                "Accept" -> "application/json")
            .withRequestTimeout(10 seconds)
            .withFollowRedirects(true)
            .withRequestFilter(AhcCurlRequestLogger())
            .get()
            .map { response => 
                val json = "_([\\w])".r.replaceAllIn(response.json.toString, {m => m.group(1).toUpperCase})
                Json.parse(json).validate[AccountEntityInternal]
            }
            .map { 
                case JsSuccess(ac, _) => 
                    Right(AccountEntity(ac))
                case e : JsError => 
                    println(s"failed to parse again")
                    Left(Error(List(JsError.toJson(e).toString)))
            }
    }
}