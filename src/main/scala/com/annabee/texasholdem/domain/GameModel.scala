package com.annabee.texasholdem.domain

import scala.util.Random
import cats.data.State

// TODO get rid of this abstraction, adds no value
sealed trait Area {
  val numberOfCards: Int
  val cards: Option[List[Card]]
  val visibility: Boolean = true
}

case class HoldCards(cards: Option[List[Card]] = None) extends Area {
  val numberOfCards = 2
  override val visibility = false
}

case class Flop(cards: Option[List[Card]] = None) extends Area {
  val numberOfCards = 3
}

case class Turn(cards: Option[List[Card]] = None) extends Area {
  val numberOfCards = 1
}

case class River(cards: Option[List[Card]] = None) extends Area {
  val numberOfCards = 1
}

case class Player(name: String, holdCards: HoldCards)

case class Game(players: List[Player], deck: Deck, flop: Flop, turn: Turn, river: River, status: Status)

object Dealer {

  def shuffle(deck: Deck): Deck = Random.shuffle(deck)

  def dealInto(area: Area): State[Game, Unit] = State { game =>
    area match {
      case f: Flop  => (game.copy(deck = game.deck.drop(f.numberOfCards), flop = Flop(Some(game.deck.take(f.numberOfCards)))), ())
      case t: Turn  => (game.copy(deck = game.deck.drop(t.numberOfCards), turn = Turn(Some(game.deck.take(t.numberOfCards)))), ())
      case r: River => (game.copy(deck = game.deck.drop(r.numberOfCards), river = River(Some(game.deck.take(r.numberOfCards)))), ())
    }
  }

  def dealPlayer(player: Player): State[Game, Unit] = State { game =>
    val updatedPlayer = Player(player.name, HoldCards(Some(game.deck.take(2))))
    (game.copy(deck = game.deck.drop(2), players = updatedPlayer :: game.players diff List(player)), ())
  }

  def discardOne: State[Game, Unit] = State { game =>
    (game.copy(deck = game.deck.tail), ())
  }
}
