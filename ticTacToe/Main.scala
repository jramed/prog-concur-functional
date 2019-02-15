package ticTacToe

import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameView, GestorIO}

object Main {

  def isTicTacToe = {
    println("invoking main.isTicTacToe")
    game.isTicTacToe.value match{
      case None => false
      case Some(a) => a.get
    }

  }
  var game = new Game
  // se debe pedir si es demo, los dos jugadores son automaticos o son humanos
  //utilizar traits para hacerlo
  def main(args: Array[String]): Unit = {
    val coordinateView: GenericCoordinateView = GameView.selectGameMode

    GameView.write(game)
    //este while se tiene que hacer como actores
    //o se pasan el valor del trablero entre los actores
    //o hay un tercer actor que gestiona el tablero
    do {
      if (!game.isComplete){
        game = game.put(coordinateView.read)
      } else {
        game = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(game)
    } while (!isTicTacToe)
    GestorIO.write("... pero has perdido")
  }
}
