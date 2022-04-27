package com.annabee.markvshaney

import com.annabee.markvshaney.markovchain.{TextBasedMarkovChain, TextDisplay, TextInputParser}

object MarkVShaneyApp extends App {

  val input = "bla"

  val chain = TextBasedMarkovChain.generate(input, 2)
  TextDisplay.show(chain)

  // step 1: process input - get possible steps
  //    a: pad
  //    b: process paragraphs - how do paragraphs end?
  //    c: process sentences within paragraph - ends with punctuation
  // step 2: analyse text and get stats
  //    a: stats on length of paragraphs
  //    b: stats on length of sentences
  //    c: possible paragraph seeds ?
  // step 3: generate a chain
  //    a: choose a random paragraph seed and
  //    b: keep generating next state until paragraph ends
  // step 4: display
  //    trim all extra padding and format
}
