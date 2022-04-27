package com.annabee.markvshaney.markovchain

import scala.util.Random

sealed trait MarkovChain[A] {

  def generate(input: A, windowSize: Int): A

}

case object TextBasedMarkovChain extends MarkovChain[String] {


  override def generate(input: String, windowSize: Int): String = {
    assert(windowSize > 0)

    val (states, seeds, stats) = TextInputParser.generateStateTransitionsAndSeedsAndStats(input, windowSize)

    val paragraphStart = Random.shuffle(seeds).head

    // nextTransition

   ???
  }

  // FIXME
//    private def nextTransition(state: List[String], transitions: Map[String, List[String]]): List[String] =
//      if (state.last.endsWith("."))  // FIXME: add all punctuation
//        state
//      else {
//        // FIXME: 1) get rid of that mkString in states generation and make all this a list 2) checking if last ends with punctuation is not enough, check all elems of the next state.
//        val nextState = transitions.get(state.last.split(" ").last) match {
//          case None => " blooper"
//          case Some(v) => Random.shuffle(v).head
//        }
//        val updatedState = state :+ nextState
//        nextTransition(updatedState, transitions)
//      }

}


