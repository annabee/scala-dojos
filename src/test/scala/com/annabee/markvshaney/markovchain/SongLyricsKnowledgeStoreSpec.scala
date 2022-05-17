package com.annabee.markvshaney.markovchain

import org.scalatest.{FlatSpec, Matchers}

class SongLyricsKnowledgeStoreSpec extends FlatSpec with Matchers {

  behavior of "TextBasedKnowledgeStoreTest"

  it should "return a selection of possible next states given a token word" in {
    // Given
    val states =  Map(List("a", "b") -> List("c", "d"), List("b", "c") -> List("d", "e"), List("c", "d") -> List("e"))
    val seeds = List.empty[List[String]]

    // When
    val result: Seq[String] = SongLyricsKnowledgeStore(states, seeds).getPossibleStateTransitions(List("a", "b"))

    // Then
    result should contain theSameElementsAs List("c", "d")
  }
}
