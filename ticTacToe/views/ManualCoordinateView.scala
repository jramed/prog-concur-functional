package ticTacToe.views

import ticTacToe.models.Coordinate
import ticTacToe.traits.GenericCoordinateView

object ManualCoordinateView extends GenericCoordinateView {

  def read:Coordinate = {
    val row = GestorIO.readInt("Fila? [1-3]")
    val column = GestorIO.readInt("Columna? [1-3]")
    new Coordinate(row-1, column-1)
  }

}

