package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait KnowledgeStore[A] {

  def getStartState: A

  def getPossibleNextStateTransitions(currentState: A): List[A]
}

case class TextBasedKnowledgeStore(stateTransitions: Map[String, List[String]],
                                   startStates: List[String],
                                   stats: TextStats) extends KnowledgeStore[String] {

  override def getStartState: String = Random.shuffle(startStates).head

  override def getPossibleNextStateTransitions(currentState: String): List[String] =
    stateTransitions.getOrElse(currentState, List.empty)

}
