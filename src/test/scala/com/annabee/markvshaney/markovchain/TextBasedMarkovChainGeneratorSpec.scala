package com.annabee.markvshaney.markovchain

import com.annabee.markvshaney.markovchain.TextBasedMarkovChainGenerator._
import org.scalatest.Matchers._
import org.scalatest.FlatSpec

class TextBasedMarkovChainGeneratorSpec extends FlatSpec {

  behavior of "TextBasedMarkovChain"

  it should "generate a chain given inout text and window size" in {
    // TODO: implement after input parsing has been implemented
    succeed
  }

  it should "build a chain biased towards ending when avg number of sentences in a paragraph is exceeded" in {
    // Given
    val currentChain = List("a", "b")
    val stateTransitions =
      Map(
        "a" -> List("b"),
        "b" -> List("c", "c", "c", "c"),
        "c" -> List("d", "d", "d", "d."),
        "d." -> List("e", "e", "e", "e"),
        "e" -> List("f", "f", "f", "f"),
        "f" -> List("g", "g", "g."),
        "g" -> List("h."),
      )
    val store = TextBasedKnowledgeStore(stateTransitions, startStates = List.empty, avgSentenceLength = 3.0, avgParagraphLength = 2.0)

    // When
    val result = buildChain(currentChain, store)

    // Then
    result should contain theSameElementsAs List("a", "b", "c", "d.", "e", "f", "g.")
  }

  it should "select next state given current chain and possible transitions" in {
    // Given
    val transitions = List("c", "c", "d", "d")

    // When
    val result = chooseNextState(transitions, currentChainLength = 3, avgSentenceLength = 5.0)

    // Then
    result should (be ("c") or be ("d"))
  }

  it should "bias next state towards a sentence ending one when current chain has exceeded avg sentence length" in {
    // Given
    val transitions = List("a", "b!", "c", "d.")

    // When
    val result = chooseNextState(transitions, currentChainLength = 4, avgSentenceLength = 3.0)

    // Then
    result should (be ("d.") or be ("b!"))
  }

  it should "tell when a word ends a sentence" in {
    // Given
    val sentenceEndingWords = List("a.", "b?", "c!", "d...")
    val notASentenceEndingWord = List("a,", "b", "c:", "")

    // When
    val ending = (for { word <- sentenceEndingWords } yield endsSentence(word)).forall(identity)
    val notEnding = (for { word <- notASentenceEndingWord } yield endsSentence(word)).exists(identity)

    // Then
    ending shouldBe true
    notEnding shouldBe false
  }

}
