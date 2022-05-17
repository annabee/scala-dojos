package com.annabee.markvshaney.markovchain

sealed trait InputParser[A] {

  def parse(input: A, windowSize: Int): KnowledgeStore[A]
}

case object SongLyricsParser extends InputParser[String] {

  override def parse(input: String, windowSize: Int): SongLyricsKnowledgeStore = {

    val (transitions, startTokens) = getStateTransitionsAndStartSeeds(input, windowSize)
    // FIXME: this would be better if it could generate verses
    // val maxVerseLength = getMaxVerseLength(input.split("\n").toList)

    SongLyricsKnowledgeStore(
      transitions,
      startTokens
    )
  }

  // returns transitions from one word to the next group of words (determined by window size), alongside a lst of possible sentence starting words
  private[markovchain] def getStateTransitionsAndStartSeeds(inputText: String, windowSize: Int): (Map[List[String], List[String]], List[List[String]]) = {

    val paragraphs = inputText.split("\n")
    val transitionsList: Array[List[(List[String], String)]] = for (paragraph <- paragraphs) yield padInput(paragraph, windowSize).split(" ")
      .sliding(windowSize + 1)
      .map(elem => elem.take(windowSize).toList -> elem.last).toList

    val stateTransitions: Map[List[String], List[String]] = transitionsList
      .flatten.groupBy(_._1).map { case (k,v) => (k,v.toList.map(_._2)) }

    val transitionsScrubbedOfPadding = stateTransitions.map {
      case (keys, values) => (keys.filter(! _.contains("/")), values.filter(! _.contains("/")))
    }.filter(_._1.nonEmpty)

    val startStates: List[List[String]] = transitionsScrubbedOfPadding
      .view.keys.toList
      .filter(elem => elem.head.nonEmpty  && elem.head.toCharArray.head.isUpper && elem.length.equals(windowSize))

    (transitionsScrubbedOfPadding, startStates)
  }

  private[markovchain] def getMaxVerseLength(paragraphs: List[String]): Int = {
    val lengths = for (paragraph <- paragraphs) yield paragraph.split(" ").length
    lengths.max
  }

  // hack  to make sure the first and last word are being included
  private[markovchain] def padInput(input: String, windowSize: Int): String = {
    val padding: String = (1 to windowSize).map(e => "/ ").mkString
    padding + input + " " + padding
  }

  // Unused in this chain, source text doesn't have punctuation
  private[markovchain] def endsSentence(word: String): Boolean = {
    val punctuation = List(".", "?", "!", "...")
    val l = for { p <- punctuation } yield word.endsWith(p)
    l.exists(identity)
  }
}
