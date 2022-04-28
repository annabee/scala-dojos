package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait MarkovChain[A] {

  def generate(input: A, windowSize: Int): List[A]
}

case object TextBasedMarkovChain extends MarkovChain[String] {

  override def generate(input: String, windowSize: Int): List[String] = {
    assert(windowSize > 0)

    val knowledgeStore = TextInputParser.parse(input, windowSize)

    val paragraphStart = knowledgeStore.getStartState

    knowledgeStore.getPossibleNextStateTransitions(paragraphStart)

    // keep track of stats, while not exceeding avg sentence and paragraph length, keep generating new states

    // nextTransition

   ???
  }

  // TODO: think about paragraph lengths
  // or maybe I won't use it at all?
  private[markovchain] def selectNextState(currentChain: List[String], possibleTransitions: List[String], stats: TextStats): String = {
    if (currentChain.length < stats.avgSentenceLength) Random.shuffle(possibleTransitions).head
    else {
      val biasedStates = possibleTransitions.filter(endingSentence)
      biasedStates.length match {
        case 0 => Random.shuffle(possibleTransitions).head
        case _ => Random.shuffle(biasedStates).head
      }
    }
  }

  private[markovchain] def endingSentence(word: String): Boolean = {
    val punctuation = List(".", "?", "!", "...")
    val l = for { p <- punctuation } yield word.endsWith(p)
    l.exists(identity)
  }
}
