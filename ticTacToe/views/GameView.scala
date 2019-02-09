package ticTacToe.views

import ticTacToe.models.Game

object GameView {

  def write(game:Game) = {
    BoardView.write(game)
    TurnView.write(game)
  }
}
