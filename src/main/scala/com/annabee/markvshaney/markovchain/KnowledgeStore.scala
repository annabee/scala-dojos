package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait KnowledgeStore[A] {

  def getStartState: A

  def getPossibleStateTransitions(currentState: A): List[A]
}

case class TextBasedKnowledgeStore(stateTransitions: Map[String, List[String]],
                                   startStates: List[String],
                                   avgSentenceLength: Double,
                                   avgParagraphLength: Double) extends KnowledgeStore[String] {

  override def getStartState: String = Random.shuffle(startStates).head

  override def getPossibleStateTransitions(currentState: String): List[String] =
    stateTransitions.getOrElse(currentState, List.empty)
}
