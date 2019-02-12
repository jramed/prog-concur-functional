package ticTacToe.views

import ticTacToe.models.Game

object GameMode extends Enumeration {
  type GameMode = Value
  val Manual, Demo = Value
}

object GameView {

  def obtainGameMode() = {

    val mode = GestorIO.readInt("Game mode? [0 Demo, Any other value Manual]")
    mode match {
      case a if (a == 0) => {
        GameMode.Demo
      }
      case _ => {
        GameMode.Manual
      }
    }
  }

  def write(game:Game) = {
    BoardView.write(game)
    TurnView.write(game)
  }
}
