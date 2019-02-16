package ticTacToe

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameView, GestorIO}

case class Play(game: Game, coordinateView: GenericCoordinateView)
case class StopGame(game: Game)


class PlayerActor0 extends Actor {
  def receive = {
    case Play(game, coordinateView) => {
      var newGame = game
      if (!game.isComplete){
        newGame = game.put(coordinateView.read)
      } else {
        newGame = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(newGame)
      if (game.isTicTacToe == false)
        sender ! Play(newGame,coordinateView)
      else {
        sender ! StopGame(game)
        context.stop(self)
      }
    }
    case StopGame(game) =>
      context.stop(self)
    case _ =>
  }
}

class PlayerActor1(player: ActorRef) extends Actor {
  def receive = {
    case Play(game, coordinateView) => {
      var newGame = game
      if (!game.isComplete){
        newGame = game.put(coordinateView.read)
      } else {
        newGame = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(newGame)

      if (game.isTicTacToe == false)
        player ! Play(newGame,coordinateView)
      else {
        player ! StopGame(game)
        context.stop(self)
      }
    }
    case StopGame(game) =>
      context.stop(self)
    case _ =>
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

    val playerActor0 = system.actorOf(Props[PlayerActor0], name = "player0")
    val playerActor1 = system.actorOf(Props(new PlayerActor1(playerActor0)), name = "player1" )

    playerActor1 ! Play(game, coordinateView)

/*    do {
      if (!game.isComplete){
        game = game.put(coordinateView.read)
      } else {
        game = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(game)
    } while (!game.isTicTacToe)
    */
    GestorIO.write("... pero has perdido")


  }
}
