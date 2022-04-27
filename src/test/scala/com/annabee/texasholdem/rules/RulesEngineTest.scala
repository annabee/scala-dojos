package com.annabee.texasholdem.rules

import com.annabee.texasholdem.domain.{Card, _}
import com.annabee.texasholdem.rules.RulesEngine._
import org.scalatest.{FlatSpec, Matchers}

class RulesEngineTest extends FlatSpec with Matchers {

  behavior of "RulesEngine"

  it should "recognise a Royal Flush" in {
    val royalFlush = Hand(List(Card(Heart, Ace), Card(Heart, King), Card(Heart, Queen), Card(Heart, Jack), Card(Heart, _10)))
    val royalFlushWrongSuits = Hand(List(Card(Heart, Ace), Card(Spade, King), Card(Heart, Queen), Card(Heart, Jack), Card(Heart, _10)))
    val notRoyalFlush = Hand(List(Card(Heart, Queen), Card(Spade, King), Card(Diamond, Queen), Card(Heart, Jack), Card(Heart, _10)))

    RoyalFlush.check(royalFlush) shouldBe true
    RoyalFlush.check(royalFlushWrongSuits) shouldBe false
    RoyalFlush.check(notRoyalFlush) shouldBe false
  }

  it should "recognise a Straight Flush" in {
    val straightFlush = Hand(List(Card(Heart, _10), Card(Heart, _9), Card(Heart, _8), Card(Heart, _7), Card(Heart, _6)))
    val straightFlushWrongSuits = Hand(List(Card(Diamond, _10), Card(Heart, _9), Card(Spade, _8), Card(Heart, _7), Card(Heart, _6)))
    val notStraightFlush = Hand(List(Card(Heart, Ace), Card(Heart, _9), Card(Heart, _8), Card(Heart, _7), Card(Heart, _6)))

    StraightFlush.check(straightFlush) shouldBe true
    StraightFlush.check(straightFlushWrongSuits) shouldBe false
    StraightFlush.check(notStraightFlush) shouldBe false
  }

  it should "recognise a Four of a Kind" in {
    val fourOfAKindHead = Hand(List(Card(Heart, _10), Card(Diamond, _10), Card(Club, _10), Card(Spade, _10), Card(Heart, _6)))
    val fourOfAKindTail = Hand(List(Card(Heart, Ace), Card(Heart, _10), Card(Diamond, _10), Card(Club, _10), Card(Spade, _10)))
    val notFourOfAKind  = Hand(List(Card(Heart, Ace), Card(Heart, _9), Card(Heart, _8), Card(Heart, _7), Card(Heart, _6)))

    FourOfAKind.check(fourOfAKindHead) shouldBe true
    FourOfAKind.check(fourOfAKindTail) shouldBe true
    FourOfAKind.check(notFourOfAKind) shouldBe false
  }

  it should "recognise a Full House" in {
    val fullHouseHead = Hand(List(Card(Heart, _10), Card(Spade, _6), Card(Diamond, _10), Card(Club, _10), Card(Heart, _6)))
    val fullHouseTail = Hand(List(Card(Diamond, _10), Card(Heart, Ace), Card(Diamond, Ace), Card(Club, _10), Card(Spade, _10)))
    val notFullHouse  = Hand(List(Card(Heart, Ace), Card(Heart, _9), Card(Heart, _8), Card(Heart, _7), Card(Heart, _6)))

    FullHouse.check(fullHouseHead) shouldBe true
    FullHouse.check(fullHouseTail) shouldBe true
    FullHouse.check(notFullHouse) shouldBe false
  }

  it should "sort a hand by rank" in {
    val hand = Hand(List(Card(Heart, _2), Card(Club, Ace), Card(Diamond, Jack), Card(Spade, _9)))

    sort(hand) shouldBe Hand(List(Card(Club, Ace), Card(Diamond, Jack), Card(Spade, _9), Card(Heart, _2)))
  }

  it should "recognise same  rank" in {
    val sameRank = List(Card(Heart, _2), Card(Club, _2), Card(Diamond, _2), Card(Spade, _2))
    val notSameRank = List(Card(Heart, _3), Card(Club, _2), Card(Diamond, _5), Card(Spade, _4))

    checkSameRank(sameRank) shouldBe true
    checkSameRank(notSameRank) shouldBe false
  }

  it should "recognise same  suit" in {
    val sameSuit = List(Card(Heart, _2), Card(Heart, _3), Card(Heart, _7), Card(Heart, Queen))
    val notSameSuit = List(Card(Heart, _3), Card(Club, _2), Card(Diamond, _5), Card(Spade, _4))

    checkSameSuit(sameSuit) shouldBe true
    checkSameSuit(notSameSuit) shouldBe false
  }

  it should "check if card are in a sequence" in {
    val sequence = List(Card(Heart, _7), Card(Heart, _6), Card(Heart, _5), Card(Heart, _4))
    val notSequence = List(Card(Heart, _3), Card(Club, _2), Card(Diamond, _5), Card(Spade, _4))

    checkCardsInSequence(sequence) shouldBe true
    checkCardsInSequence(notSequence) shouldBe false
  }

}
