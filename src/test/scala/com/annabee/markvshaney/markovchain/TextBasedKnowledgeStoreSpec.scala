package com.annabee.markvshaney.markovchain

import org.scalatest.{FlatSpec, Matchers}

class TextBasedKnowledgeStoreSpec extends FlatSpec with Matchers {

  behavior of "TextBasedKnowledgeStoreTest"

  it should "return a selection of possible next states given a token word" in {
    // Given
    val states =  Map("a" -> List("b"), "b" -> List("c"), "c" -> List("d"))
    val seeds = List.empty[String]

    // When
    val result = TextBasedKnowledgeStore(states, seeds, 0.0, 0.0)
      .getPossibleStateTransitions("a")

    // Then
    result should contain theSameElementsAs List("b")
  }
}
