package ticTacToe.models

class Turn(value: Int = 0) {

  private val value_ = value

  def take: Int = value_

  def changed: Turn = new Turn((this.value_ + 1) % 2)

  override def equals(that: Any): Boolean =
    that match {
      case that: Turn =>
        this.value_ == that.value_
      case _ => false
    }
}
