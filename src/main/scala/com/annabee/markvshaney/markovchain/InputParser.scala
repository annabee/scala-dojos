package com.annabee.markvshaney.markovchain

sealed trait InputParser[A] {
  // TODO refactor; maybe a type for output
  def generateStateTransitionsAndSeedsAndStats(input: A, windowSize: Int): (Map[A, A], List[A], Stats)
}

case object TextInputParser extends InputParser[String] {

  override def generateStateTransitionsAndSeedsAndStats(input: String, windowSize: Int): (Map[String, String], List[String], TextStats) = {
    val (paragraphs, paragraphStats) = parseParagraphs(input)
    val seeds = getWordsStartingParagraphs(paragraphs)
    val (states, sentenceStats) = parseSentences(paragraphs, windowSize)

    (states, seeds, TextStats(paragraphStats, sentenceStats))
  }

  // returns a list of all paragraphs in text with the avg number of sentences in a paragraph
  def parseParagraphs(input: String): (List[String], Double) = ???

  // return a list of seeds to paragraphs
  def getWordsStartingParagraphs(paragraphs: List[String]): List[String] = ???

  // returns transitions from one word to the next group of words (determined by window size), alongside stats about avg sentence length
  def parseSentences(input: List[String], windowSize: Int): (Map[String, String], Double) = {
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
