package ticTacToe

import akka.actor.{ActorSystem, Props}
import ticTacToe.actors.{PlayerActor0, PlayerActor1}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.GameView

object Main {
  // var is not needed anymore
  val game = new Game

  def main(args: Array[String]): Unit = {
    //Change #1: Ask for Manual or Demo mode
    val coordinateView: GenericCoordinateView = GameView.selectGameMode

    GameView.write(game)

    //Change #3: Use actors to play the game. The while loop has been removed
    val system = ActorSystem("TicTacToe")

    val playerActor0 = system.actorOf(Props(new PlayerActor0(coordinateView)), name = "player0")
    val playerActor1 = system.actorOf(Props(new PlayerActor1(playerActor0, coordinateView)), name = "player1" )

    playerActor1 ! StartPlay(game)
  }
}
