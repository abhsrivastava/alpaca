package models

case class Error(errorMessages: List[String]) extends Exception(errorMessages.mkString(","))