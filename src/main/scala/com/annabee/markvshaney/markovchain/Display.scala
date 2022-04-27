package com.annabee.markvshaney.markovchain


sealed trait Display[A] {
  def show(chain: A): Unit
}

case object TextDisplay extends Display[String] {
  override def show(chain: String): Unit = ???
}
