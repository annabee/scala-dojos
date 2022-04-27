package com.annabee.texasholdem

import com.annabee.texasholdem.domain._
import cats.data.State
import cats.implicits.catsSyntaxApplicativeId

object TexasHoldemPokerApp extends App {

  val deck = for {
    suit <- List(Club, Spade, Heart, Diamond)
    rank <- List(Ace, King, Queen, Jack, _10, _9, _8, _7, _6, _5, _4, _3, _2)
  } yield Card(suit, rank)

  val players = List(
    Player("Anna", HoldCards()),
    Player("Pete", HoldCards()),
    Player("Emily", HoldCards()),
    Player("Sam", HoldCards())
  )

  val initialState = Game(players, deck, Flop(None), Turn(None), River(None), InProgress)

  val dealPlayers: List[State[Game, Unit]] = for {
    player <- players
  } yield Dealer.dealPlayer(player)

  val gameSequence = dealPlayers :+
  Dealer.discardOne :+
  Dealer.dealInto(initialState.flop) :+
  Dealer.discardOne :+
  Dealer.dealInto(initialState.turn) :+
  Dealer.discardOne :+
  Dealer.dealInto(initialState.river)

  // TODO figure  out how to collapse this sequence :)

  //  type GameState[A] = State[Game, A]
//  gameSequence.foldRight(0.pure[GameState]){
//    (a, b) => a.run(initialState).value
//  }
//

}
