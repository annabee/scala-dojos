package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait KnowledgeStore[A] {

  def getStartState: List[A]

  def getPossibleStateTransitions(currentState: List[A]): List[A]
}

case class SongLyricsKnowledgeStore(stateTransitions: Map[List[String], List[String]],
                                    startStates: List[List[String]],
                                    //maxVerseLength: Int
                                    //maxNumberOfVerses: Int
                                  ) extends KnowledgeStore[String] {

  override def getStartState: List[String] = Random.shuffle(startStates).head

  override def getPossibleStateTransitions(currentState: List[String]): List[String] =
    stateTransitions.getOrElse(currentState, List.empty)
}
