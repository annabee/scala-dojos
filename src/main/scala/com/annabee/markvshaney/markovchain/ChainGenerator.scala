package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait MarkovChainGenerator[A] {

  def generate(input: A, windowSize: Int, maxChainLength: Int): List[List[A]]
}

case object SongLyricsMarkovChainGenerator extends MarkovChainGenerator[String] {

  // pass in the KnowledgeStore instead
  override def generate(input: String, windowSize: Int, maxChainLength: Int): List[List[String]] = {
    assert(windowSize > 0)

    val knowledgeStore: SongLyricsKnowledgeStore = SongLyricsParser.parse(input, windowSize)

    // FIXME magic number 20 is the number of chains, i.e. verses
    (1 to 20).toList.map(e => {
      val paragraphStart = knowledgeStore.getStartState
      buildChain(paragraphStart, knowledgeStore, windowSize, maxChainLength)
    })
  }

  private[markovchain] def buildChain(currentChain: List[String],
                                      knowledgeStore: SongLyricsKnowledgeStore,
                                      windowSize: Int,
                                      maxChainLength: Int): List[String] = {

    val chainLength = currentChain.length

    if (chainLength >= maxChainLength)
      currentChain
    else {
      val seedForNextState = currentChain.drop(chainLength - windowSize)
      val possibleNextStates = knowledgeStore.stateTransitions.get(seedForNextState)

      possibleNextStates match {
        case Some(states) =>
          if (states.isEmpty) {
            currentChain
          } else {
            val next = Random.shuffle(states).head
            val newChain = currentChain :+ next
            buildChain(newChain, knowledgeStore, windowSize, maxChainLength)
          }
        case None => currentChain
      }
    }
  }
}
