package models

import java.time.{Instant, ZonedDateTime}
import play.api.libs.json._
import play.api.libs.json.JsonNaming.SnakeCase
import config.AlpacaConfig

object AccountStatus extends Enumeration {
    type AccountStatus = Value
    val Onboarding = Value("ONBOARDING")
    val SubmissionFailed = Value("SUBMISSION_FAILED")
    val Submitted = Value("SUBMITTED")
    val AccountUpdted = Value("ACCOUNT_UPDATED")
    val ApprovalPending = Value("APPROVAL_PENDING")
    val Active = Value("ACTIVE")
    val Rejected = Value("REJECTED")
    implicit val format = Json.formatEnum(this)
}

case class AccountEntityInternal(
    id: String,
    accountNumber: String,
    status: AccountStatus.Value,
    currency: String,
    buyingPower: String,
    regtBuyingPower: String,
    daytradingBuyingPower: String,
    cash: String,
    portfolioValue: String,
    patternDayTrader: Boolean,
    tradingBlocked: Boolean,
    transfersBlocked: Boolean,
    accountBlocked: Boolean,
    createdAt: Instant,
    tradeSuspendedByUser: Boolean,
    multiplier: String,
    shortingEnabled: Boolean,
    equity: String,
    lastEquity: String,
    longMarketValue: String,
    shortMarketValue: String,
    initialMargin: String,
    maintenanceMargin: String,
    lastMaintenanceMargin: String, 
    sma: String,
    daytradeCount: Int
)

object AccountEntityInternal {
    import AccountStatus._
    implicit val config = JsonConfiguration(SnakeCase)
    import ai.x.play.json.Jsonx
    implicit lazy val jsonFormat = Jsonx.formatCaseClass[AccountEntityInternal]    
}

case class AccountEntity(
    id: String,
    accountNumber: String,
    status: AccountStatus.Value,
    currency: String,
    buyingPower: Double,
    regtBuyingPower: Double,
    daytradingBuyingPower: Double,
    cash: Double,
    portfolioValue: Double,
    patternDayTrader: Boolean,
    tradingBlocked: Boolean,
    transfersBlocked: Boolean,
    accountBlocked: Boolean,
    createdAt: ZonedDateTime,
    tradeSuspendedByUser: Boolean,
    multiplier: Int,
    shortingEnabled: Boolean,
    equity: Double, 
    lastEquity: Double, 
    longMarketValue: Double, 
    shortMarketValue: Double,
    initialMargin: Double,
    maintenanceMargin: Double, 
    lastMaintenanceMargin: Double, 
    sma: Int,
    daytradeCount: Int
)

object AccountEntity {
    def apply(input: AccountEntityInternal, c: AlpacaConfig) : AccountEntity = {
        AccountEntity(
            input.id,
            input.accountNumber,
            input.status,
            input.currency,
            input.buyingPower.toDouble,
            input.regtBuyingPower.toDouble,
            input.daytradingBuyingPower.toDouble,
            input.cash.toDouble,
            input.portfolioValue.toDouble,
            input.patternDayTrader,
            input.tradingBlocked,
            input.transfersBlocked,
            input.accountBlocked,
            input.createdAt.atZone(c.zoneId),
            input.tradeSuspendedByUser,
            input.multiplier.toInt,
            input.shortingEnabled,
            input.equity.toDouble, 
            input.lastEquity.toDouble, 
            input.longMarketValue.toDouble, 
            input.shortMarketValue.toDouble,
            input.initialMargin.toDouble,
            input.maintenanceMargin.toDouble, 
            input.lastMaintenanceMargin.toDouble, 
            input.sma.toInt,
            input.daytradeCount      
        )
    }
}
