package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait MarkovChainGenerator[A] {

  def generate(input: A, windowSize: Int): List[A]
}

case object TextBasedMarkovChainGenerator extends MarkovChainGenerator[String] {


  override def generate(input: String, windowSize: Int): List[String] = {
    assert(windowSize > 0)

    val knowledgeStore: TextBasedKnowledgeStore = TextInputParser.parse(input, windowSize)

    val paragraphStart = knowledgeStore.getStartState

    buildChain(List(paragraphStart), knowledgeStore)
  }

  private[markovchain] def buildChain(currentChain: List[String], knowledgeStore: TextBasedKnowledgeStore): List[String] = {
    if (noOfSentencesInChain(currentChain) >= knowledgeStore.avgParagraphLength && endsSentence(currentChain.last))
        currentChain
    else {
      val nextState = chooseNextState(knowledgeStore.getPossibleStateTransitions(currentChain.last), currentChain.length, knowledgeStore.avgSentenceLength)
      buildChain(currentChain :+ nextState, knowledgeStore)
    }
  }

  private[markovchain] def chooseNextState(possibleTransitions: List[String], currentChainLength: Int, avgSentenceLength: Double): String = {
    if (currentChainLength < avgSentenceLength) Random.shuffle(possibleTransitions).head
    else {
      val biasedStates = possibleTransitions.filter(endsSentence)
      biasedStates.length match {
        case 0 => Random.shuffle(possibleTransitions).head
        case _ => Random.shuffle(biasedStates).head
      }
    }
  }

  private[markovchain] def endsSentence(word: String): Boolean = {
    val punctuation = List(".", "?", "!", "...")
    val l = for { p <- punctuation } yield word.endsWith(p)
    l.exists(identity)
  }

  private[markovchain] def noOfSentencesInChain(chain: List[String]): Int = {
    chain.count(endsSentence)
  }
}
