package com.annabee.texasholdem.domain

sealed trait Suit { val symbol: String }

case object Club    extends Suit { val symbol = "\u2663" }
case object Spade   extends Suit { val symbol = "\u2664" }
case object Heart   extends Suit { val symbol = "\u2665" }
case object Diamond extends Suit { val symbol = "\u2666" }

trait Rank {
  val value: Int
}

case object Ace   extends Rank {
  val value = 14

  override def toString: String = "Ace"
}
case object King  extends Rank {
  val value = 13

  override def toString: String = "King"
}
case object Queen extends Rank {
  val value = 12

  override def toString: String = "Queen"
}
case object Jack  extends Rank {
  val value = 11

  override def toString: String = "Jack"
}
case object _10   extends Rank {
  val value = 10

  override def toString: String = "10"
}
case object _9    extends Rank {
  val value = 9

  override def toString: String = "9"
}
case object _8    extends Rank {
  val value = 8

  override def toString: String = "8"
}
case object _7    extends Rank {
  val value = 7

  override def toString: String = "7"
}
case object _6    extends Rank {
  val value = 6

  override def toString: String = "6"
}
case object _5    extends Rank {
  val value = 5

  override def toString: String = "5"
}
case object _4    extends Rank {
  val value = 4

  override def toString: String = "4"
}
case object _3    extends Rank {
  val value = 3

  override def toString: String = "3"
}
case object _2    extends Rank {
  val value = 2

  override def toString: String = "2"
}

case class Card(suit: Suit, rank: Rank) {
  override def toString: String = s"${rank.value}${suit.symbol}"
}
