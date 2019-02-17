package ticTacToe

import akka.actor.{ActorSystem, Props}
import ticTacToe.actors.{PlayerActor0, PlayerActor1}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.GameView

object Main {

  var game = new Game
  // se debe pedir si es demo, los dos jugadores son automaticos o son humanos
  //utilizar traits para hacerlo
  def main(args: Array[String]): Unit = {
    val coordinateView: GenericCoordinateView = GameView.selectGameMode

    GameView.write(game)
    //este while se tiene que hacer como actores
    //o se pasan el valor del trablero entre los actores
    //o hay un tercer actor que gestiona el tablero

    val system = ActorSystem("TicTacToe")

    val playerActor0 = system.actorOf(Props(new PlayerActor0(coordinateView)), name = "player0")
    val playerActor1 = system.actorOf(Props(new PlayerActor1(playerActor0, coordinateView)), name = "player1" )

    playerActor1 ! StartPlay(game)
  }
}
