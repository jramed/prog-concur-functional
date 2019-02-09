package ticTacToe

import ticTacToe.models.Coordinate
import ticTacToe.traits.GenericCoordinateView

object DemoCoordinateView extends GenericCoordinateView{
  def read:Coordinate = {
    val row = getRow()
    val column = getColumn()
    new Coordinate(row-1, column-1)
  }

  private def getRow(): Int = {
    1
  }

  private def getColumn(): Int = {
    1
  }

}
