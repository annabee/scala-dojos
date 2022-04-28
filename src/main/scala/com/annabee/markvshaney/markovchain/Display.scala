package com.annabee.markvshaney.markovchain


sealed trait Display[A] {
  def show(chain: List[A]): Unit
}

case object TextDisplay extends Display[String] {
  override def show(chain: List[String]): Unit = ???
}
