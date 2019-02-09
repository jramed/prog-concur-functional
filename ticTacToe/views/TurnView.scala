package ticTacToe.views

import ticTacToe.models.Game

object TurnView {

  def write(game: Game) = {
    val color = ColorView.getChar(game.take)
    GestorIO.write(s"Es el turno de $color\n")
  }

}
