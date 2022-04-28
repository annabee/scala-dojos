package com.annabee.markvshaney.markovchain

import org.scalatest.FlatSpec

class TextBasedKnowledgeStoreTest extends FlatSpec {

  behavior of "TextBasedKnowledgeStoreTest"

  it should "getStartState should throw an exception when there are no start states" in {
    val states =  Map.empty[String, List[String]]
    val seeds = List.empty[String]
    val stats = TextStats(0.0, 0.0)

    assertThrows(TextBasedKnowledgeStore(states, seeds, stats).getStartState)
  }

  it should "getNexStateTransition" in {
    ???
  }

}
