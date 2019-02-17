package ticTacToe.views

import ticTacToe.DemoCoordinateView
import ticTacToe.models.Game

object GameMode extends Enumeration {
  type GameMode = Value
  val Manual, Demo = Value
}

object GameView {
  def selectGameMode = {
    def askForGameMode() = {
      val mode = GestorIO.readInt("Game mode? [0 Demo, Any other value for Manual]")
      mode match {
        case a if (a == 0) => GameMode.Demo
        case _ => GameMode.Manual
      }
    }

    askForGameMode() match {
      case mode if mode == GameMode.Demo => DemoCoordinateView
      case _ => ManualCoordinateView
    }
  }

  def write(game:Game) = {
    BoardView.write(game)
    TurnView.write(game)
  }
}
