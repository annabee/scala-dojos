package com.annabee.markvshaney.markovchain

import com.annabee.markvshaney.markovchain.SongLyricsParser._
import org.scalatest.{FlatSpec, Matchers}

class SongLyricsParserSpec extends FlatSpec with Matchers {

  behavior of "TextInputParserSpec"

  it should "parse" in {

  }

//  it should "parse text into paragraphs, extract words starting paragraphs and calculate avg number of sentences in a paragraph" in {
//    // Given
//    val inputText =
//      """This is a paragraph. This paragraph has exactly 2 sentences, all in one line.
//        |
//        |This is a second paragraph. This paragraph has 4 sentences. There are 2 paragraphs in my text. I'm expecting the avg number of sentences in a paragraph to be 3.
//        |
//        |""".stripMargin
//
//    // When
//    val result = processParagraphs(inputText)
//
//    // Then
//    result.paragraphs should contain theSameElementsAs List("This is a paragraph. This paragraph has exactly 2 sentences, all in one line.",
//      "This is a second paragraph. This paragraph has 4 sentences. There are 2 paragraphs in my text. I'm expecting the avg number of sentences in a paragraph to be 3.")
//
//    result.startWords should contain theSameElementsAs List("This", "This")
//
//    result.avgNumberOfSentencesInAParagraph should be(3.0)
//  }

  it should "create a map of all possible transitions from one token to the next group of words" in {
    // Given
    val input =
      "Anna likes dogs and cats. Pete only likes cats.\nThey will have to have the talk one day."

    // When
    val (actualTransitions, actualStartWords) = getStateTransitionsAndStartSeeds(input, 2)

    // Then
    val expectedTransitions = Map(
      List("to", "have") -> List("the"),
      List("talk", "one") -> List("day."),
      List("have", "to") -> List("have"),
      List("They", "will") -> List("have"),
      List("only", "likes") -> List("cats."),
      List("Anna", "likes") -> List("dogs"),
      List("They") -> List("will"), List("and", "cats.") -> List("Pete"),
      List("cats.") -> List(), List("likes", "dogs") -> List("and"), List("Pete", "only") -> List("likes"),
      List("cats.", "Pete") -> List("only"), List("dogs", "and") -> List("cats."), List("Anna") -> List("likes"),
      List("day.") -> List(), List("likes", "cats.") -> List(), List("will", "have") -> List("to"),
      List("the", "talk") -> List("one"), List("one", "day.") -> List(), List("have", "the") -> List("talk"))

    val expectedStartWords = List(List("They", "will"), List("Anna", "likes"), List("Pete", "only"))

    actualTransitions should contain theSameElementsAs expectedTransitions
    actualStartWords should contain theSameElementsAs expectedStartWords
  }

  it should "pad input" in {
    // Given
    val input = "This is a sentence with some words in it."
    // When
    val result = padInput(input, 3)
    // Then
    result should be("/ / / " + input + " / / / ")
  }
//
//  it should "check if a word ends a sentence" in {
//    // Given
//    val wordsEndingSentence = List("a.", "b?", "c!", "d...")
//    // Then
//    wordsEndingSentence.forall(endsSentence) shouldBe true
//
//    // Given
//    val wordsNotEndingSentence = List("a", "b", "c", "d")
//    // Then
//    wordsNotEndingSentence.exists(endsSentence) shouldBe false
//
//  }
}
