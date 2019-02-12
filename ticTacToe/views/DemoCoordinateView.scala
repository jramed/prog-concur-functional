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
    //nanoTime() is more expensive than currentTimeMillis() depending on the architecture
    val r = new scala.util.Random(System.nanoTime())
    r.nextInt(3)+1
  }

  private def getColumn(): Int = {
    getRow()
  }

}
