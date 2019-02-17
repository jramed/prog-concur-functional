package ticTacToe

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameView, GestorIO}

case class StartPlay(game: Game)
case class NextMovement(game: Game)
case class StopGame(game: Game)


class PlayerActor0(coordinateView: GenericCoordinateView) extends Actor {
  def receive = {
    case NextMovement(game) => {
      var newGame = game
      if (!game.isComplete){
        newGame = game.put(coordinateView.read)
      } else {
        newGame = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(newGame)
      if (game.isTicTacToe == false) {
        println("not finished yet player0")
        sender ! NextMovement(newGame)
        println("not finished yet player0_2")
      }
      else {
        sender ! StopGame(game)
        GestorIO.write("... pero has perdido")
        context.stop(self)
      }
    }
    case StopGame(game) =>
      context.stop(self)
    case _ => println("received non expected message in player0")
  }
}

class PlayerActor1(player: ActorRef, coordinateView: GenericCoordinateView) extends Actor {
  def receive = {
    case StartPlay(game)  => {
      var newGame = game
      if (!game.isComplete) {
        newGame = game.put(coordinateView.read)
      } else {
        newGame = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(newGame)
      player ! NextMovement(newGame)
    }
    case NextMovement(game) => {
      var newGame = game
      if (!game.isComplete){
        newGame = game.put(coordinateView.read)
      } else {
        newGame = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(newGame)

      if (newGame.isTicTacToe == false) {
        println("not finished yet player1")
        sender ! NextMovement(newGame)
      }
      else {
        sender ! StopGame(newGame)
        GestorIO.write("... pero has perdido")
        context.stop(self)
      }
    }
    case StopGame(game) =>
      context.stop(self)
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

/*    do {
      if (!game.isComplete){
        game = game.put(coordinateView.read)
      } else {
        game = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(game)
    } while (!game.isTicTacToe)
    */
    //GestorIO.write("... pero has perdido")
  }
}
