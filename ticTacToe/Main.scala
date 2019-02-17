package ticTacToe

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameView, GestorIO}

case class StartPlay(game: Game)
case class NextMovement(game: Game)
case class StopGame(game: Game)

abstract class PlayerActor(coordinateView: GenericCoordinateView) extends Actor {
  protected def playGame(game: Game) = {
    if (!game.isComplete) {
      val newGame = game.put(coordinateView.read)
      GameView.write(newGame)
      newGame
    } else {
      val newGame = game.move(coordinateView.read, coordinateView.read)
      GameView.write(newGame)
      newGame
    }
  }

  protected def playGameAndCheck(game: Game) = {
    val newGame = playGame(game)

    if (newGame.isTicTacToe == false) {
      sender ! NextMovement(newGame)
    }
    else {
      sender ! StopGame(newGame)
      GestorIO.write("... pero has perdido\n")
      context.stop(self)
    }
  }
}

class PlayerActor0(coordinateView: GenericCoordinateView) extends PlayerActor(coordinateView) {
  def receive = {
    case NextMovement(game) => playGameAndCheck(game)
    case StopGame(game) => {
      context.stop(self)
      println("StopGame of player0")
      context.system.terminate()
    }
    case _ => println("received non expected message in player0")
  }
}

class PlayerActor1(player: ActorRef, coordinateView: GenericCoordinateView) extends PlayerActor(coordinateView) {
  def receive = {
    case StartPlay(game)  => player ! NextMovement(playGame(game))
    case NextMovement(game) => playGameAndCheck(game)
    case StopGame(game) => {
      context.stop(self)
      println("StopGame of player1")
      context.system.terminate()
    }
    case _ => println("received non expected message in player1")
  }
}

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
