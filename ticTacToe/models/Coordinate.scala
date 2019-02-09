package ticTacToe.models

class Coordinate(row:Int, column:Int) {

  private val row_ = row

  private val column_ = column

  def getDirection(that:Coordinate):String =
    if (this.row_ == that.row_)
      "Horizontal"
    else if (this.column_ == that.column_)
      "Vertical"
    else if (this.inDiagonal && that.inDiagonal)
      "Diagonal"
    else if (this.inInverse && that.inInverse)
      "Inverse"
    else
      ""

  private def inDiagonal : Boolean =
    this.row_ - this.column_ == 0

  private def inInverse : Boolean =
    this.row_ + this.column_ == 3-1

  override def equals(that: Any): Boolean =
    that match {
      case that: Coordinate =>
        this.row_ == that.row_ &&
          this.column_ == that.column_
      case _ => false
    }

  def getRow = row_

  def getColumn = column_
}
