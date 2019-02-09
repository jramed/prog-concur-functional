package ticTacToe.views

import ticTacToe.models.{Coordinate, Game}

object BoardView {

  def write(game: Game) = {
    var result = ""
    for (i <- List(0, 1, 2)) {
      for (j <- List(0, 1, 2)) {
        val color = game.getColor(new Coordinate(i, j))
        result += ColorView.getChar(color)
      }
      result += "\n"
    }
    println(result)
  }
}
