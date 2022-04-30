package com.annabee.markvshaney.markovchain

sealed trait InputParser[A] {

  def parse(input: A, windowSize: Int): KnowledgeStore[A]
}

case object TextInputParser extends InputParser[String] {

  override def parse(input: String, windowSize: Int): TextBasedKnowledgeStore = {
    val (paragraphs, paragraphStats) = parseParagraphs(input)
    val seeds = getWordsStartingParagraphs(paragraphs)
    val (states, sentenceStats) = parseSentences(paragraphs, windowSize)

    TextBasedKnowledgeStore(states, seeds, paragraphStats, sentenceStats)
  }

  // returns a list of all paragraphs in text with the avg number of sentences in a paragraph
  private def parseParagraphs(input: String): (List[String], Double) = ???

  // return a list of seeds to paragraphs
  private def getWordsStartingParagraphs(paragraphs: List[String]): List[String] = ???

  // returns transitions from one word to the next group of words (determined by window size), alongside stats about avg sentence length
  private def parseSentences(input: List[String], windowSize: Int): (Map[String, List[String]], Double) = {
    // for each paragraph
    // padInput(input, windowSize)

    // FIXME: needs to retain the last word in a sentence
    //    input.split(" ").sliding(windowSize)
    //      .map(elem => elem.head -> elem.tail).toList
    //      .groupMap(_._1)(_._2)
    //      .view.mapValues(elem => elem.map(_.mkString(" "))).toMap

    ???
  }

  // hack  to make sure the first and last word are being included
  private def padInput(input: String, windowSize: Int): String = ???
}
