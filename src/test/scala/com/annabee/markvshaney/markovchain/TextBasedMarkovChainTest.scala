package com.annabee.markvshaney.markovchain

import com.annabee.markvshaney.markovchain.TextBasedMarkovChain._
import org.scalatest.Matchers._
import org.scalatest.FlatSpec

class TextBasedMarkovChainTest extends FlatSpec {

  behavior of "TextBasedMarkovChain"

  it should "select next state" in {

  }

  it should "tell when a word ends a sentence" in {
    val sentenceEndingWords = List("a.", "b?", "c!", "d...")
    val notASentenceEndingWord = List("a,", "b", "c:", "")

    val ending = (for { word <- sentenceEndingWords } yield endingSentence(word)).forall(identity)
    val notEnding = (for { word <- notASentenceEndingWord } yield endingSentence(word)).exists(identity)

    ending shouldBe true
    notEnding shouldBe false
  }

}
