package com.annabee.markvshaney.markovchain

sealed trait Stats

case class TextStats(avgParagraphLength: Double, avgSentenceLength: Double) extends Stats
