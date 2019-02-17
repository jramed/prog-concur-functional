package ticTacToe.actors

import ticTacToe.{NextMovement, StopGame}
import ticTacToe.traits.GenericCoordinateView

class PlayerActor0(coordinateView: GenericCoordinateView) extends PlayerActor(coordinateView) {
  def receive: PartialFunction[Any,Unit] = {
    case NextMovement(game) => playGameAndCheck(game)
    case StopGame(game) => stopGame()
    case _ => println("received non expected message in player0")
  }
}
