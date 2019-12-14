package models

import java.time.{ZonedDateTime, Instant}
import play.api.libs.json._
import play.api.libs.json.JsonNaming._
import config.AlpacaConfig
import java.time.format.DateTimeFormatter

case class ClockEntityInternal(
    timestamp: Instant,
    isOpen: Boolean,
    nextOpen: Instant,
    nextClose: Instant
)
object ClockEntityInternal {
    implicit val config = JsonConfiguration(SnakeCase)
    implicit val reads = Json.reads[ClockEntityInternal]
    implicit val writes = Json.writes[ClockEntityInternal]
}
case class ClockEntity(
    timestamp: ZonedDateTime,
    isOpen: Boolean,
    nextOpen: ZonedDateTime,
    nextClose: ZonedDateTime
) {
    val df = DateTimeFormatter.ofPattern("MMM dd YYYY h:mma")
    val friendlyCurrentTime = df.format(timestamp)
    val friendlyNextOpen = df.format(nextOpen)
    val friendlyNextClose = df.format(nextClose)
    val friendlyIsOpen = if(isOpen) "Yes" else "No"
}
object ClockEntity {
    def apply(input: ClockEntityInternal, c: AlpacaConfig) : ClockEntity = {
        ClockEntity(
            input.timestamp.atZone(c.zoneId),
            input.isOpen,
            input.nextOpen.atZone(c.zoneId),
            input.nextClose.atZone(c.zoneId)
        )
    }
}