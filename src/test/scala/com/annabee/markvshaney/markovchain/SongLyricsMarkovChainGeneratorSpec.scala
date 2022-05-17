package com.annabee.markvshaney.markovchain

import com.annabee.markvshaney.markovchain.SongLyricsMarkovChainGenerator._
import com.annabee.markvshaney.markovchain.SongLyricsParser.endsSentence
import org.scalatest.Matchers._
import org.scalatest.FlatSpec

class SongLyricsMarkovChainGeneratorSpec extends FlatSpec {

  behavior of "TextBasedMarkovChain"

  it should "generate a chain given input text and window size" in {
    // TODO: implement after input parsing has been implemented
    succeed
  }

  it should "build a chain biased towards ending when max length is exceeded" in {
    // Given
    val currentChain = List("a", "b")
    val stateTransitions =
      Map(
        List("a", "b") -> List("c"),
        List("b", "c") -> List("d"),
        List("c", "d") -> List("e"),
        List("d", "e") -> List("f")
      )
    val store = SongLyricsKnowledgeStore(stateTransitions, startStates = List.empty)

    // When
    val result = buildChain(currentChain, store, 2, 5)

    // Then
    result should contain theSameElementsAs List("a", "b", "c", "d", "e")
  }

  // Not used, saving for later
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
