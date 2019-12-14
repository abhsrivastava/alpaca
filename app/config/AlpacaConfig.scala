package config

import javax.inject._
import play.api.Configuration
import java.time.ZoneId

class AlpacaConfig @Inject()(c: Configuration) {
    val endpoint = c.get[String]("alpaca.endpoint")
    val keyId = c.get[String]("alpaca.keyId")
    val secret = c.get[String]("alpaca.secret")
    val zoneId = ZoneId.of(c.get[String]("alpaca.zoneId"))
}