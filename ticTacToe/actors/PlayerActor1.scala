package ticTacToe.actors

import akka.actor.ActorRef
import ticTacToe.{NextMovement, StartPlay, StopGame}
import ticTacToe.traits.GenericCoordinateView

class PlayerActor1(player: ActorRef, coordinateView: GenericCoordinateView) extends PlayerActor(coordinateView) {
  def receive: PartialFunction[Any,Unit] = {
    case StartPlay(game)  => player ! NextMovement(playGame(game))
    case NextMovement(game) => playGameAndCheck(game)
    case StopGame(game) => stopGame()
    case _ => println("received non expected message in player1")
  }
}
