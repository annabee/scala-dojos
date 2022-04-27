package com.annabee.texasholdem.domain

class DealerTest extends org.scalatest.FunSpec {

  describe("Dealer::dealInto") {
    // FIXME: put in guards for running deck size
    it ("should handle dealing into different areas") {
      val deck = List(Card(Heart, Ace), Card(Club, Ace), Card(Diamond, Ace), Card(Spade, Ace))
      val initialGame = Game(List.empty, deck, Flop(None), Turn(None), River(None), InProgress)

      val expected = Game(List.empty, List(Card(Spade, Ace)), Flop(Some(List(Card(Heart, Ace), Card(Club, Ace), Card(Diamond, Ace)))), Turn(None), River(None), InProgress)
      val result = Dealer.dealInto(initialGame.flop).runS(initialGame).value

      assert(result.equals(expected))
    }

    it ("should handle dealing cards to Players") {
      val deck = List(Card(Heart, Ace), Card(Club, Ace))
      val player1 = Player("Test1", HoldCards())
      val player2 = Player("Test2", HoldCards())
      val initialGame = Game(List(player1, player2), deck, Flop(None), Turn(None), River(None), InProgress)

      val expected = Game(List(Player("Test1", HoldCards(Some(List(Card(Heart, Ace), Card(Club, Ace))))), Player("Test2", HoldCards())), List.empty, Flop(None), Turn(None), River(None), InProgress)
      val result = Dealer.dealPlayer(player1).runS(initialGame).value

      assert(result.equals(expected))
    }

    it ("should handle discarding cards") {
      val deck = List(Card(Heart, Ace))
      val initialGame = Game(List.empty, deck, Flop(None), Turn(None), River(None), InProgress)

      val expected = Game(List.empty, List.empty, Flop(None), Turn(None), River(None), InProgress)
      val result = Dealer.discardOne.runS(initialGame).value

      assert(result.equals(expected))
    }
  }
}
