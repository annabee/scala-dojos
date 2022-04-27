package com.annabee.texasholdem.rules

import com.annabee.texasholdem.domain._

object RulesEngine {

  // TODO should just be a type alias or gone altogether
  case class Hand(cards: List[Card])

  trait HandType {
    def check(hand: Hand): Boolean
  }

  //  Royal flush: A-K-Q-J-10, all the same suit
  case object RoyalFlush extends HandType {
    override def check(hand: Hand): Boolean = { sort(hand).cards match {
        case a :: b :: c :: d :: e :: Nil  =>
          if (a.rank.value.equals(Ace.value) &&
              b.rank.value.equals(King.value) &&
              c.rank.value.equals(Queen.value) &&
              d.rank.value.equals(Jack.value) &&
              e.rank.value.equals(_10.value) &&
              checkSameSuit(hand.cards)) true
          else false
        case _ => false
      }
    }
  }

  //  Straight flush: Five cards in a sequence, all in the same suit
  case object StraightFlush extends HandType {
    override def check(hand: Hand): Boolean = {
      val sorted = sort(hand).cards
      checkCardsInSequence(sorted) && checkSameSuit(sorted)
    }
  }

  //  Four of a kind: All four cards of the same rank
  case object FourOfAKind extends HandType {
    override def check(hand: Hand): Boolean = {
      val sorted = sort(hand)
      if (checkSameRank(sorted.cards.take(4)) || checkSameRank(sorted.cards.tail)) true
      else false
    }
  }

  //  Full house: Three of a kind with a pair
  case object FullHouse extends HandType {
    override def check(hand: Hand): Boolean = { sort(hand).cards match {
      case a :: b :: c :: d :: e :: Nil  =>
        if (checkSameRank(List(a, b, c)) && checkSameRank(List(d, e)) || checkSameRank(List(a, b)) && checkSameRank(List(c, d, e))) true
        else false
      case _ => false
    }
    }
  }

  //  Flush: Any five cards of the same suit, but not in a sequence
  case object Flush extends HandType {
    override def check(hand: Hand): Boolean = {
      val sorted = sort(hand).cards
      checkSameSuit(sorted) && !checkCardsInSequence(sorted)
    }
  }


  //  Straight: Five cards in a sequence, but not of the same suit
  case object Straight extends HandType {
    override def check(hand: Hand): Boolean = {
      val sorted = sort(hand).cards
      checkCardsInSequence(sorted) && ! checkSameSuit(sorted)
    }
  }


  //  Three of a kind: Three cards of the same rank
  case object ThreeOfAKind extends HandType {
    override def check(hand: Hand): Boolean = ???
  }

  //  Two pair: Two different pairs
  case object TwoPair extends HandType {
    override def check(hand: Hand): Boolean = ???
  }

  //  Pair: Two cards of the same rank
  case object Pair extends HandType {
    override def check(hand: Hand): Boolean = ???
  }

  //  High Card: When you haven't made any of the hands above, the highest card wins
  case object HighCard extends HandType {
    override def check(hand: Hand): Boolean = ???
  }


  // HELPERS
  private[rules] def sort(hand: Hand): Hand = Hand(hand.cards.sortBy(card => card.rank.value).reverse)

  private[rules] def checkSameSuit(cards: List[Card]): Boolean = cards.map(_.suit).forall(_ == cards.head.suit)

  private[rules] def checkSameRank(cards: List[Card]): Boolean = cards.map(_.rank).forall(_ == cards.head.rank)

  private[rules] def checkCardsInSequence(cards: List[Card]): Boolean = cards.zip(cards.tail).forall { case (a, b) => (a.rank.value - b.rank.value) == 1 }


  // TODO
  def chooseWinningHand(hand1: Hand, hand2: Hand): Hand = ???

  def getHandType(hand: Hand): HandType = ???
}
