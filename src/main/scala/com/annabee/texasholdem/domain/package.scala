package com.annabee.texasholdem

package object domain {

  type Deck = List[Card]

  sealed trait Status
  case object InProgress extends Status
  case class Finished(winner: Player) extends Status


}
