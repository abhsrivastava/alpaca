package services
import config.AlpacaConfig

class BaseService(c: AlpacaConfig) {
    def getHeaders() : List[(String, String)] = {
        List(
            "APCA-API-KEY-ID" -> c.keyId, 
            "APCA-API-SECRET-KEY" -> c.secret,
            "Accept" -> "application/json"
        )
    }
}